package Bengkel;


/**
 * Mekanik bengkel. Aturan bisnis: hanya boleh menangani satu servis aktif.
 */
public class Mekanik {
    private String idMekanik;
    private String nama;
    private String spesialisasi;
    private boolean sibuk;
    private Servis servisAktif;

    /** Konstruktor default. */
    public Mekanik() {
        this.idMekanik = "";
        this.nama = "";
        this.spesialisasi = "";
        this.sibuk = false;
        this.servisAktif = null;
    }

    /** Konstruktor berparameter; divalidasi lewat setter. */
    public Mekanik(String idMekanik, String nama, String spesialisasi) {
        this();
        setIdMekanik(idMekanik);
        setNama(nama);
        setSpesialisasi(spesialisasi);
    }

    // ---------- Getter ----------
    public String getIdMekanik() { return idMekanik; }
    public String getNama() { return nama; }
    public String getSpesialisasi() { return spesialisasi; }
    public boolean isSibuk() { return sibuk; }
    public Servis getServisAktif() { return servisAktif; }

    // ---------- Setter (dengan validasi) ----------
    public void setIdMekanik(String idMekanik) {
        if (idMekanik == null || idMekanik.trim().isEmpty()) {
            throw new IllegalArgumentException("ID mekanik tidak boleh kosong.");
        }
        this.idMekanik = idMekanik.trim().toUpperCase();
    }

    public void setNama(String nama) {
        if (nama == null || nama.trim().isEmpty()) {
            throw new IllegalArgumentException("Nama mekanik tidak boleh kosong.");
        }
        this.nama = nama.trim();
    }

    public void setSpesialisasi(String spesialisasi) {
        if (spesialisasi == null || spesialisasi.trim().isEmpty()) {
            throw new IllegalArgumentException("Spesialisasi tidak boleh kosong.");
        }
        this.spesialisasi = spesialisasi.trim();
    }

    // ---------- Method perilaku ----------
    public boolean isTersedia() {
        return !sibuk;
    }

    /** Aturan bisnis BR1: satu mekanik hanya satu servis aktif. */
    public boolean tugaskan(Servis servis) {
        if (servis == null) {
            System.out.println("Error: servis tidak valid.");
            return false;
        }
        if (this.sibuk) {
            System.out.println("Error: mekanik " + this.nama
                    + " sedang menangani servis lain.");
            return false;
        }
        this.servisAktif = servis;
        this.sibuk = true;
        return true;
    }

    /** Membebaskan mekanik setelah servis selesai. */
    public void selesaikanTugas() {
        this.servisAktif = null;
        this.sibuk = false;
    }

    public void tampilkanInfo() {
        System.out.println(idMekanik + " | " + nama + " | " + spesialisasi
                + " | " + (sibuk ? "Sibuk (" + servisAktif.getKodeServis() + ")" : "Tersedia"));
    }
}
