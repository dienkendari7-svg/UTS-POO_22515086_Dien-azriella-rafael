package Bengkel;

public class service {
    public String getKodeServis() {
        String s = "";
        return s;
    }
}


/**
 * Transaksi servis yang menghubungkan Kendaraan dan Mekanik.
 * Aturan bisnis: biaya total = biaya jasa + biaya suku cadang.
 */
class Servis {
    public static final String STATUS_AKTIF = "AKTIF";
    public static final String STATUS_SELESAI = "SELESAI";

    private String kodeServis;
    private Kendaraan kendaraan;
    private Mekanik mekanik;
    private String keluhan;
    private double biayaJasa;
    private double biayaSukuCadang;
    private String status;

    /** Konstruktor default. */
    public Servis() {
        this.kodeServis = "";
        this.kendaraan = null;
        this.mekanik = null;
        this.keluhan = "";
        this.biayaJasa = 0;
        this.biayaSukuCadang = 0;
        this.status = STATUS_AKTIF;
    }

    /** Konstruktor berparameter; status awal selalu AKTIF. */
    public Servis(String kodeServis, Kendaraan kendaraan, Mekanik mekanik,
                  String keluhan, double biayaJasa, double biayaSukuCadang) {
        this();
        if (kendaraan == null || mekanik == null) {
            throw new IllegalArgumentException("Kendaraan dan mekanik wajib ada.");
        }
        setKodeServis(kodeServis);
        this.kendaraan = kendaraan;
        this.mekanik = mekanik;
        setKeluhan(keluhan);
        setBiayaJasa(biayaJasa);
        setBiayaSukuCadang(biayaSukuCadang);
    }

    // ---------- Getter ----------
    public String getKodeServis() { return kodeServis; }
    public Kendaraan getKendaraan() { return kendaraan; }
    public Mekanik getMekanik() { return mekanik; }
    public String getKeluhan() { return keluhan; }
    public double getBiayaJasa() { return biayaJasa; }
    public double getBiayaSukuCadang() { return biayaSukuCadang; }
    public String getStatus() { return status; }

    // ---------- Setter (dengan validasi) ----------
    public void setKodeServis(String kodeServis) {
        if (kodeServis == null || kodeServis.trim().isEmpty()) {
            throw new IllegalArgumentException("Kode servis tidak boleh kosong.");
        }
        this.kodeServis = kodeServis.trim();
    }

    public void setKeluhan(String keluhan) {
        if (keluhan == null || keluhan.trim().isEmpty()) {
            throw new IllegalArgumentException("Keluhan tidak boleh kosong.");
        }
        this.keluhan = keluhan.trim();
    }

    public void setBiayaJasa(double biayaJasa) {
        if (biayaJasa < 0) {
            throw new IllegalArgumentException("Biaya jasa tidak boleh negatif.");
        }
        this.biayaJasa = biayaJasa;
    }

    public void setBiayaSukuCadang(double biayaSukuCadang) {
        if (biayaSukuCadang < 0) {
            throw new IllegalArgumentException("Biaya suku cadang tidak boleh negatif.");
        }
        this.biayaSukuCadang = biayaSukuCadang;
    }

    // ---------- Method perilaku ----------
    /** Aturan bisnis BR2: biaya total = jasa + suku cadang. */
    public double hitungBiayaTotal() {
        return this.biayaJasa + this.biayaSukuCadang;
    }

    /** Menyelesaikan servis dan membebaskan kendaraan serta mekanik. */
    public void selesaikan() {
        if (STATUS_SELESAI.equals(this.status)) {
            System.out.println("Error: servis " + kodeServis + " sudah selesai.");
            return;
        }
        this.status = STATUS_SELESAI;
        this.kendaraan.selesaiServis();
        this.mekanik.selesaikanTugas();
    }

    public void tampilkanInfo() {
        System.out.println(kodeServis + " | " + kendaraan.getPlatNomor()
                + " | Mekanik: " + mekanik.getNama()
                + " | Keluhan: " + keluhan
                + " | Total: Rp" + String.format("%,.0f", hitungBiayaTotal())
                + " | " + status);
    }
}