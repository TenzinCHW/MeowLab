package CSE.Lab3;// package Week3;

import java.util.Arrays;

public class BankImpl {
    private int numberOfCustomers;    // the number of customers
    private int numberOfResources;    // the number of resources
    private int[] available;    // the available amount of each resource
    private final int[][] maximum;    // the maximum demand of each customer
    private int[][] allocation;    // the amount currently allocated
    private int[][] need;        // the remaining needs of each customer

    public BankImpl(int[] resources, int numberOfCustomers) {
        numberOfResources = resources.length;
        this.numberOfCustomers = numberOfCustomers;
        available = resources;
        maximum = new int[numberOfCustomers][numberOfResources];
        allocation = new int[numberOfCustomers][numberOfResources];
        need = new int[numberOfCustomers][numberOfResources];
    }

    public int getNumberOfCustomers() {
        return numberOfCustomers;
    }

    public void addCustomer(int customerNumber, int[] maximumDemand) {
        if (customerNumber > numberOfCustomers) return;
        for (int i = 0; i < numberOfResources; i++) {
            maximum[customerNumber][i] = maximumDemand[i];
            need[customerNumber][i] = maximumDemand[i];
        }
    }

    public void getState() {
        System.out.println("Available resources");
        System.out.println(Arrays.toString(available));
        System.out.println("Maximum resources for each process");
        for (int i = 0; i < numberOfCustomers; i++) {
            System.out.println(Arrays.toString(maximum[i]));
        }
        System.out.println("Allocated resources for each process");
        for (int i = 0; i < numberOfCustomers; i++) {
            System.out.println(Arrays.toString(allocation[i]));
        }
        System.out.println("Extra resources processes may need");
        for (int i = 0; i < numberOfCustomers; i++) {
            System.out.println(Arrays.toString(need[i]));
        }
    }

    public synchronized boolean requestResources(int customerNumber, int[] request) {
        String req = Arrays.toString(request);
        System.out.println("Customer number " + customerNumber + " requests for " + req.substring(1, req.length() - 1));
        for (int i = 0; i < numberOfResources; i++) {
            if (request[i] > need[customerNumber][i]) {
                System.out.println("Request denied");
                return false;
            }
        }
        for (int i = 0; i < numberOfResources; i++) {
            if (request[i] > available[i]) {
                System.out.println("Request denied");
                return false;
            }
        }
        // try to allocate reources
        for (int i = 0; i < numberOfResources; i++) {
            available[i] -= request[i];
            allocation[customerNumber][i] += request[i];
            need[customerNumber][i] -= request[i];
        }

        if (checkSafe(customerNumber, request)) {
            System.out.println("Request granted");
            return true;
        }
        releaseResources(customerNumber, request);
        System.out.println("Request rejected");
        return false;
    }

    public synchronized void releaseResources(int customerNumber, int[] release) {
        System.out.println("Customer number " + customerNumber + " releasing " + Arrays.toString(release));
        // release the resources from customer customerNumber
        for (int i = 0; i < numberOfResources; i++) {
            available[i] += release[i];
            allocation[customerNumber][i] -= release[i];
            need[customerNumber][i] += release[i];
        }
    }

    private synchronized boolean checkSafe(int customerNumber, int[] request) {
        int work[] = new int[numberOfResources];
        boolean finish[] = new boolean[numberOfCustomers];


        for (int i = 0; i < numberOfResources; i++) {
            work[i] = available[i];
        }

        for (int i = 0; i < numberOfCustomers; i++) {
            finish[i] = false;
        }

        boolean needLTEWorkAndFinishIFalse = true;
        while (needLTEWorkAndFinishIFalse) {
            needLTEWorkAndFinishIFalse = false;
            for (int i = 0; i < numberOfCustomers; i++) {
                if (!finish[i]) {
                    boolean processSatisfies = true;
                    for (int j = 0; j < numberOfResources; j++) {
                        if (!(need[i][j] <= work[j])) {
                            processSatisfies = false;
                        }
                    }
                    if (processSatisfies) {
                        for (int j = 0; j < numberOfResources; j++) {
                            work[j] += allocation[i][j];
                        }
                        finish[i] = true;
                        needLTEWorkAndFinishIFalse = true;
                    }
                }
            }
        }
        boolean toReturn = true;
        for (int i = 0; i < numberOfCustomers; i++) {
            if (!finish[i]) toReturn = false;
        }
        return toReturn;
    }
}