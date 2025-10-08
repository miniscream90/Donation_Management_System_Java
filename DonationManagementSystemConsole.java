import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

// ----------------- Donor Class -----------------
class Donor {
    private String donorId;
    private String name;
    private String email;
    private double totalDonated;

    public Donor(String donorId, String name, String email, double totalDonated) {
        this.donorId = donorId;
        this.name = name;
        this.email = email;
        this.totalDonated = totalDonated;
    }

    public String getDonorId() { return donorId; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public double getTotalDonated() { return totalDonated; }

    public void addDonation(double amount) { totalDonated += amount; }
}

// ----------------- Donation System Backend -----------------
class DonationSystem {
    private HashMap<String, Donor> donors = new HashMap<>();
    private final String DONOR_FILE = "donors.txt";
    private final String DONATION_FILE = "donations.txt";
    private final String FUND_USAGE_FILE = "fund_usage.txt";

    public DonationSystem() {
        loadDonors();
    }

    public boolean registerDonor(String donorId, String name, String email) {
        if (donors.containsKey(donorId)) return false;
        donors.put(donorId, new Donor(donorId, name, email, 0.0));
        saveDonors();
        return true;
    }

    public Donor findDonor(String donorId) {
        return donors.get(donorId);
    }

    public void updateDonor(Donor donor) {
        donors.put(donor.getDonorId(), donor);
        saveDonors();
    }

    // ---------- File Handling for Donors ----------
    private void loadDonors() {
        try (BufferedReader br = new BufferedReader(new FileReader(DONOR_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 4) {
                    String donorId = parts[0];
                    String name = parts[1];
                    String email = parts[2];
                    double totalDonated = Double.parseDouble(parts[3]);
                    donors.put(donorId, new Donor(donorId, name, email, totalDonated));
                }
            }
        } catch (IOException e) {
            System.out.println("No existing donors found, starting fresh.");
        }
    }

    private void saveDonors() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(DONOR_FILE))) {
            for (Donor d : donors.values()) {
                pw.println(d.getDonorId() + "," + d.getName() + "," + d.getEmail() + "," + d.getTotalDonated());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // ---------- Donation and Fund Usage Handling ----------
    public void logDonation(String donorId, double amount) {
        String timestamp = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(new Date());
        try (PrintWriter pw = new PrintWriter(new FileWriter(DONATION_FILE, true))) {
            pw.println(donorId + ",Donated ₹" + amount + " on " + timestamp);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void logFundUsage(String purpose, double amount) {
        String timestamp = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(new Date());
        try (PrintWriter pw = new PrintWriter(new FileWriter(FUND_USAGE_FILE, true))) {
            pw.println("Used ₹" + amount + " for " + purpose + " on " + timestamp);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<String> getDonations(String donorId) {
        List<String> donations = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(DONATION_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.startsWith(donorId + ",")) {
                    donations.add(line.substring(donorId.length() + 1));
                }
            }
        } catch (IOException e) {
            donations.add("No donations found.");
        }
        return donations;
    }

    public List<String> getFundUsage() {
        List<String> usage = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(FUND_USAGE_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                usage.add(line);
            }
        } catch (IOException e) {
            usage.add("No fund usage records found.");
        }
        return usage;
    }
}

// ----------------- Main Class with Console Interface -----------------
public class DonationManagementSystemConsole {
    private static DonationSystem donationSystem = new DonationSystem();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        if (login()) {
            showMenu();
        }
    }

    private static boolean login() {
        System.out.println("=== Donation Management System Login ===");
        System.out.print("Username: ");
        String user = scanner.nextLine();
        System.out.print("Password: ");
        String pass = scanner.nextLine();
        if (user.equals("admin") && pass.equals("admin123")) {
            System.out.println("Login Successful!");
            return true;
        } else {
            System.out.println("Invalid Credentials!");
            return false;
        }
    }

    private static void showMenu() {
        while (true) {
            System.out.println("\n=== Donation Management System ===");
            System.out.println("1. Register Donor");
            System.out.println("2. Add Donation");
            System.out.println("3. View Donations");
            System.out.println("4. Log Fund Usage");
            System.out.println("5. View Fund Usage");
            System.out.println("6. Logout");
            System.out.print("Choose an option: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    registerDonor();
                    break;
                case "2":
                    addDonation();
                    break;
                case "3":
                    viewDonations();
                    break;
                case "4":
                    logFundUsage();
                    break;
                case "5":
                    viewFundUsage();
                    break;
                case "6":
                    System.out.println("Logged out successfully!");
                    return;
                default:
                    System.out.println("Invalid option! Please try again.");
            }
        }
    }

    private static void registerDonor() {
        System.out.print("Enter Donor ID: ");
        String id = scanner.nextLine();
        System.out.print("Enter Donor Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter Donor Email: ");
        String email = scanner.nextLine();
        if (id.isEmpty() || name.isEmpty() || email.isEmpty()) {
            System.out.println("All fields are required!");
            return;
        }
        if (donationSystem.registerDonor(id, name, email)) {
            System.out.println("Donor registered successfully!");
        } else {
            System.out.println("Donor ID already exists!");
        }
    }

    private static void addDonation() {
        System.out.print("Enter Donor ID: ");
        String id = scanner.nextLine();
        Donor donor = donationSystem.findDonor(id);
        if (donor != null) {
            System.out.print("Enter donation amount: ");
            try {
                double amount = Double.parseDouble(scanner.nextLine());
                if (amount > 0) {
                    donor.addDonation(amount);
                    donationSystem.updateDonor(donor);
                    donationSystem.logDonation(id, amount);
                    System.out.println("Donation recorded!");
                } else {
                    System.out.println("Invalid amount!");
                }
            } catch (NumberFormatException ex) {
                System.out.println("Invalid amount!");
            }
        } else {
            System.out.println("Donor not found!");
        }
    }

    private static void viewDonations() {
        System.out.print("Enter Donor ID: ");
        String id = scanner.nextLine();
        List<String> donations = donationSystem.getDonations(id);
        System.out.println("\n=== Donation History ===");
        for (String d : donations) {
            System.out.println(d);
        }
    }

    private static void logFundUsage() {
        System.out.print("Enter purpose of fund usage: ");
        String purpose = scanner.nextLine();
        System.out.print("Enter amount used: ");
        try {
            double amount = Double.parseDouble(scanner.nextLine());
            if (amount > 0 && !purpose.isEmpty()) {
                donationSystem.logFundUsage(purpose, amount);
                System.out.println("Fund usage recorded!");
            } else {
                System.out.println("Invalid amount or purpose!");
            }
        } catch (NumberFormatException ex) {
            System.out.println("Invalid amount!");
        }
    }

    private static void viewFundUsage() {
        List<String> usage = donationSystem.getFundUsage();
        System.out.println("\n=== Fund Usage Records ===");
        for (String u : usage) {
            System.out.println(u);
        }
    }
}