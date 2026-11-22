package Bengkel;

import java.time.Year;

/**
 * Kendaraan milik pelanggan yang diservis di bengkel.
 * Plat nomor menjadi kunci unik sekaligus kunci pengurutan pada ADT.
 */
public class Kendaraan {
    private static final int TAHUN_MIN = 1980;

    private String platNomor;
    private String merek;
    private String tipe;
    private int tahun;
    private boolean sedangDiservis;

    /** Konstruktor default dengan nilai awal yang aman. */
    public Kendaraan() {
        this.platNomor = "";
        this.merek = "";
        this.tipe = "";
        this.tahun = TAHUN_MIN;
        this.sedangDiservis = false;
    }

    /** Konstruktor berparameter; semua nilai divalidasi lewat setter. */
    public Kendaraan(String platNomor, String merek, String tipe, int tahun) {
        this();
        setPlatNomor(platNomor);
        setMerek(merek);
        setTipe(tipe);
        setTahun(tahun);
    }

    // ---------- Getter ----------
    public String getPlatNomor() { return platNomor; }
    public String getMerek() { return merek; }
    public String getTipe() { return tipe; }
    public int getTahun() { return tahun; }
    public boolean isSedangDiservis() { return sedangDiservis; }

    // ---------- Setter (dengan validasi) ----------
    public void setPlatNomor(String platNomor) {
        if (platNomor == null || platNomor.trim().isEmpty()) {
            throw new IllegalArgumentException("Plat nomor tidak boleh kosong.");
        }
        this.platNomor = platNomor.trim().toUpperCase();
    }

    public void setMerek(String merek) {
        if (merek == null || merek.trim().isEmpty()) {
            throw new IllegalArgumentException("Merek tidak boleh kosong.");
        }
        this.merek = merek.trim();
    }

    public void setTipe(String tipe) {
        if (tipe == null || tipe.trim().isEmpty()) {
            throw new IllegalArgumentException("Tipe tidak boleh kosong.");
        }
        this.tipe = tipe.trim();
    }

    public void setTahun(int tahun) {
        int tahunSekarang = Year.now().getValue();
        if (tahun < TAHUN_MIN || tahun > tahunSekarang) {
            throw new IllegalArgumentException(
                    "Tahun harus antara " + TAHUN_MIN + " dan " + tahunSekarang + ".");
        }
        this.tahun = tahun;
    }

    // ---------- Method perilaku ----------
    /** Menandai kendaraan sedang dalam servis. */
    public void mulaiServis() {
        this.sedangDiservis = true;
    }

    /** Menandai servis kendaraan selesai. */
    public void selesaiServis() {
        this.sedangDiservis = false;
    }

    /** Menghitung usia kendaraan pada tahun tertentu. */
    public int hitungUsia(int tahunSekarang) {
        return tahunSekarang - this.tahun;
    }

    public void tampilkanInfo() {
        System.out.println(platNomor + " | " + merek + " " + tipe + " | " + tahun
                + " | " + (sedangDiservis ? "Sedang diservis" : "Tersedia"));
    }
}