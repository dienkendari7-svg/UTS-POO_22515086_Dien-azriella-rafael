package Bengkel;

import java.util.ArrayList;

/**
 * Pengelola utama bengkel: menyimpan kendaraan (ADT), mekanik, dan servis,
 * serta menegakkan aturan bisnis.
 */
public class Bengkel {
    private String nama;
    private String alamat;
    private double totalPendapatan;
    private int counterServis;
    private ArrayList<Mekanik> daftarMekanik;
    private ArrayList<Servis> daftarServis;
    private DaftarKendaraanTerurut daftarKendaraan;

    /** Konstruktor default. */
    public Bengkel() {
        this("Bengkel Tanpa Nama", "-", 50);
    }

    /** Konstruktor berparameter. */
    public Bengkel(String nama, String alamat, int kapasitas) {
        setNama(nama);
        setAlamat(alamat);
        this.daftarKendaraan = new DaftarKendaraanTerurut(kapasitas); // kapasitas divalidasi di ADT
        this.daftarMekanik = new ArrayList<>();
        this.daftarServis = new ArrayList<>();
        this.totalPendapatan = 0;
        this.counterServis = 0;
    }

    // ---------- Getter ----------
    public String getNama() { return nama; }
    public String getAlamat() { return alamat; }
    public double getTotalPendapatan() { return totalPendapatan; }
    public DaftarKendaraanTerurut getDaftarKendaraan() { return daftarKendaraan; }

    // ---------- Setter (dengan validasi) ----------
    public void setNama(String nama) {
        if (nama == null || nama.trim().isEmpty()) {
            throw new IllegalArgumentException("Nama bengkel tidak boleh kosong.");
        }
        this.nama = nama.trim();
    }

    public void setAlamat(String alamat) {
        if (alamat == null || alamat.trim().isEmpty()) {
            throw new IllegalArgumentException("Alamat tidak boleh kosong.");
        }
        this.alamat = alamat.trim();
    }

    // ---------- Kendaraan & Mekanik ----------
    public boolean tambahKendaraan(Kendaraan k) {
        return daftarKendaraan.insert(k);
    }

    public boolean tambahMekanik(Mekanik m) {
        if (m == null) {
            System.out.println("Error: data mekanik tidak valid.");
            return false;
        }
        if (cariMekanik(m.getIdMekanik()) != null) {
            System.out.println("Error: ID mekanik " + m.getIdMekanik() + " sudah terdaftar.");
            return false;
        }
        daftarMekanik.add(m);
        return true;
    }

    public Kendaraan cariKendaraan(String plat) {
        int idx = daftarKendaraan.search(plat);
        return (idx == -1) ? null : daftarKendaraan.get(idx);
    }

    public Mekanik cariMekanik(String id) {
        if (id == null) return null;
        for (Mekanik m : daftarMekanik) {
            if (m.getIdMekanik().equalsIgnoreCase(id.trim())) return m;
        }
        return null;
    }

    // ---------- Servis ----------
    /** Membuat servis baru; menegakkan BR1 dan BR4. */
    public boolean buatServis(String plat, String idMekanik, String keluhan,
                              double jasa, double suku) {
        Kendaraan k = cariKendaraan(plat);
        if (k == null) {
            System.out.println("Error: kendaraan tidak ditemukan.");
            return false;
        }
        if (k.isSedangDiservis()) { // BR4
            System.out.println("Error: kendaraan " + k.getPlatNomor() + " sedang dalam servis.");
            return false;
        }
        Mekanik m = cariMekanik(idMekanik);
        if (m == null) {
            System.out.println("Error: mekanik tidak ditemukan.");
            return false;
        }
        String kode = String.format("SRV-%03d", counterServis + 1);
        Servis s = new Servis(kode, k, m, keluhan, jasa, suku); // validasi biaya (BR3) di sini
        if (!m.tugaskan(s)) { // BR1
            return false;
        }
        k.mulaiServis();
        daftarServis.add(s);
        counterServis++;
        System.out.println("Servis " + kode + " dibuat. Total biaya: Rp"
                + String.format("%,.0f", s.hitungBiayaTotal()));
        return true;
    }

    public boolean selesaikanServis(String kode) {
        if (kode == null || kode.trim().isEmpty()) {
            System.out.println("Error: kode servis tidak boleh kosong.");
            return false;
        }
        for (Servis s : daftarServis) {
            if (s.getKodeServis().equalsIgnoreCase(kode.trim())) {
                if (Servis.STATUS_SELESAI.equals(s.getStatus())) {
                    System.out.println("Error: servis " + s.getKodeServis() + " sudah selesai.");
                    return false;
                }
                s.selesaikan();
                totalPendapatan += s.hitungBiayaTotal();
                return true;
            }
        }
        System.out.println("Error: servis dengan kode " + kode + " tidak ditemukan.");
        return false;
    }

    // ---------- Tampilan ----------
    public void tampilkanSemuaMekanik() {
        if (daftarMekanik.isEmpty()) {
            System.out.println("(Belum ada mekanik)");
            return;
        }
        for (Mekanik m : daftarMekanik) m.tampilkanInfo();
    }

    public void tampilkanSemuaServis() {
        if (daftarServis.isEmpty()) {
            System.out.println("(Belum ada servis)");
            return;
        }
        for (Servis s : daftarServis) s.tampilkanInfo();
    }

    public void tampilkanLaporan() {
        int aktif = 0, selesai = 0;
        for (Servis s : daftarServis) {
            if (Servis.STATUS_AKTIF.equals(s.getStatus())) aktif++;
            else selesai++;
        }
        System.out.println("=== LAPORAN " + nama + " (" + alamat + ") ===");
        System.out.println("Jumlah kendaraan : " + daftarKendaraan.size()
                + " / " + daftarKendaraan.getKapasitas());
        System.out.println("Jumlah mekanik   : " + daftarMekanik.size());
        System.out.println("Servis aktif     : " + aktif);
        System.out.println("Servis selesai   : " + selesai);
        System.out.println("Total pendapatan : Rp" + String.format("%,.0f", totalPendapatan));
    }
}
