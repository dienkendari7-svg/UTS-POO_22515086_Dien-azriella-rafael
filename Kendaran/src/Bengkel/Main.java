package Bengkel;


import java.util.Scanner;

/**
 * Menu console aplikasi bengkel. Input divalidasi agar tidak crash.
 */
public class Main {
    private static final Scanner SC = new Scanner(System.in);
    private static final int KAPASITAS_KENDARAAN = 10;
    private static Bengkel bengkel;

    public static void main(String[] args) {
        bengkel = new Bengkel("Bengkel Maju Jaya", "Kendari", KAPASITAS_KENDARAAN);
        boolean jalan = true;
        while (jalan) {
            tampilkanMenu();
            int pilihan = bacaInt("Pilih menu: ");
            try {
                switch (pilihan) {
                    case 1: menuKendaraan(); break;
                    case 2: menuMekanik(); break;
                    case 3: menuServis(); break;
                    case 4: menuCari(); break;
                    case 5: menuAdt(); break;
                    case 6: bengkel.tampilkanLaporan(); break;
                    case 7:
                        System.out.println("Terima kasih.");
                        jalan = false;
                        break;
                    default: System.out.println("Error: menu tidak tersedia.");
                }
            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    private static void tampilkanMenu() {
        System.out.println("\n=== BENGKEL ===");
        System.out.println("1. Tambah & tampilkan kendaraan");
        System.out.println("2. Tambah & tampilkan mekanik");
        System.out.println("3. Servis (buat / selesaikan / tampilkan)");
        System.out.println("4. Cari kendaraan / mekanik");
        System.out.println("5. Operasi ADT (Sorted List)");
        System.out.println("6. Laporan");
        System.out.println("7. Keluar");
    }

    private static void menuKendaraan() {
        String plat = bacaString("Plat nomor : ");
        String merek = bacaString("Merek      : ");
        String tipe = bacaString("Tipe       : ");
        int tahun = bacaInt("Tahun      : ");
        if (bengkel.tambahKendaraan(new Kendaraan(plat, merek, tipe, tahun))) {
            System.out.println("Kendaraan berhasil ditambahkan.");
        }
        bengkel.getDaftarKendaraan().tampilkan();
    }

    private static void menuMekanik() {
        String id = bacaString("ID mekanik   : ");
        String nama = bacaString("Nama         : ");
        String spes = bacaString("Spesialisasi : ");
        if (bengkel.tambahMekanik(new Mekanik(id, nama, spes))) {
            System.out.println("Mekanik berhasil ditambahkan.");
        }
        bengkel.tampilkanSemuaMekanik();
    }

    private static void menuServis() {
        System.out.println("1. Buat servis  2. Selesaikan servis  3. Tampilkan servis");
        int p = bacaInt("Pilih: ");
        if (p == 1) {
            String plat = bacaString("Plat kendaraan  : ");
            String id = bacaString("ID mekanik      : ");
            String keluhan = bacaString("Keluhan         : ");
            double jasa = bacaDouble("Biaya jasa      : ");
            double suku = bacaDouble("Biaya suku cadang: ");
            bengkel.buatServis(plat, id, keluhan, jasa, suku);
        } else if (p == 2) {
            String kode = bacaString("Kode servis: ");
            if (bengkel.selesaikanServis(kode)) {
                System.out.println("Servis selesai.");
            }
        } else if (p == 3) {
            bengkel.tampilkanSemuaServis();
        } else {
            System.out.println("Error: pilihan tidak valid.");
        }
    }

    private static void menuCari() {
        System.out.println("1. Cari kendaraan (plat)  2. Cari mekanik (ID)");
        int p = bacaInt("Pilih: ");
        if (p == 1) {
            Kendaraan k = bengkel.cariKendaraan(bacaString("Plat: "));
            if (k == null) System.out.println("Data tidak ditemukan.");
            else k.tampilkanInfo();
        } else if (p == 2) {
            Mekanik m = bengkel.cariMekanik(bacaString("ID: "));
            if (m == null) System.out.println("Data tidak ditemukan.");
            else m.tampilkanInfo();
        } else {
            System.out.println("Error: pilihan tidak valid.");
        }
    }

    private static void menuAdt() {
        DaftarKendaraanTerurut adt = bengkel.getDaftarKendaraan();
        System.out.println("1. insert  2. remove  3. search  4. get(index)");
        System.out.println("5. tampilkan  6. size / isEmpty / isFull");
        int p = bacaInt("Pilih: ");
        switch (p) {
            case 1:
                String plat = bacaString("Plat nomor : ");
                String merek = bacaString("Merek      : ");
                String tipe = bacaString("Tipe       : ");
                int tahun = bacaInt("Tahun      : ");
                if (adt.insert(new Kendaraan(plat, merek, tipe, tahun))) {
                    System.out.println("Insert berhasil.");
                }
                break;
            case 2:
                if (adt.remove(bacaString("Plat yang dihapus: "))) {
                    System.out.println("Remove berhasil.");
                }
                break;
            case 3:
                int idx = adt.search(bacaString("Plat dicari: "));
                System.out.println(idx == -1 ? "Data tidak ditemukan." : "Ditemukan pada indeks " + idx);
                break;
            case 4:
                Kendaraan k = adt.get(bacaInt("Indeks: "));
                if (k != null) k.tampilkanInfo();
                break;
            case 5:
                break;
            case 6:
                System.out.println("size=" + adt.size() + ", isEmpty=" + adt.isEmpty()
                        + ", isFull=" + adt.isFull());
                break;
            default:
                System.out.println("Error: pilihan tidak valid.");
                return;
        }
        System.out.println("--- Isi ADT ---");
        adt.tampilkan();
    }

    // ---------- Pembaca input tervalidasi ----------
    private static int bacaInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Integer.parseInt(SC.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Error: masukkan angka bulat yang valid.");
            }
        }
    }

    private static double bacaDouble(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Double.parseDouble(SC.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Error: masukkan angka yang valid.");
            }
        }
    }

    private static String bacaString(String prompt) {
        while (true) {
            System.out.print(prompt);
            String s = SC.nextLine().trim();
            if (!s.isEmpty()) return s;
            System.out.println("Error: input tidak boleh kosong.");
        }
    }
}
