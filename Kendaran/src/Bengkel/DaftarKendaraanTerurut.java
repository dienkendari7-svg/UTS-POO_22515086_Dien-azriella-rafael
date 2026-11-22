package Bengkel;


/**
 * ADT Sorted List (array terurut) untuk Kendaraan, terurut menurut plat nomor.
 * Atribut internal private; pemakai hanya memakai operasi publik.
 */
public class DaftarKendaraanTerurut {
    private final int KAPASITAS;
    private Kendaraan[] data;
    private int jumlah;

    /** Membuat daftar kosong dengan kapasitas tetap. */
    public DaftarKendaraanTerurut(int kapasitas) {
        if (kapasitas <= 0) {
            throw new IllegalArgumentException("Kapasitas harus lebih dari 0.");
        }
        this.KAPASITAS = kapasitas;
        this.data = new Kendaraan[kapasitas];
        this.jumlah = 0;
    }

    public int size() { return jumlah; }
    public boolean isEmpty() { return jumlah == 0; }
    public boolean isFull() { return jumlah == KAPASITAS; }
    public int getKapasitas() { return KAPASITAS; }

    /** Sisip tetap terurut. Menolak jika penuh atau plat duplikat. */
    public boolean insert(Kendaraan k) {
        if (k == null) {
            System.out.println("Error: data kendaraan tidak valid.");
            return false;
        }
        if (isFull()) {
            System.out.println("Error: daftar kendaraan penuh (kapasitas " + KAPASITAS + ").");
            return false;
        }
        if (search(k.getPlatNomor()) != -1) {
            System.out.println("Error: plat nomor " + k.getPlatNomor() + " sudah terdaftar.");
            return false;
        }
        int i = jumlah - 1;
        // geser elemen yang lebih besar ke kanan
        while (i >= 0 && data[i].getPlatNomor().compareToIgnoreCase(k.getPlatNomor()) > 0) {
            data[i + 1] = data[i];
            i--;
        }
        data[i + 1] = k;
        jumlah++;
        return true;
    }

    /** Hapus berdasarkan plat. Menangani kosong dan tidak ditemukan. */
    public boolean remove(String plat) {
        if (isEmpty()) {
            System.out.println("Error: daftar kendaraan kosong.");
            return false;
        }
        int idx = search(plat);
        if (idx == -1) {
            System.out.println("Error: plat " + plat + " tidak ditemukan.");
            return false;
        }
        for (int i = idx; i < jumlah - 1; i++) {
            data[i] = data[i + 1]; // geser ke kiri
        }
        data[jumlah - 1] = null;
        jumlah--;
        return true;
    }

    /** Binary search. Mengembalikan indeks, atau -1 jika tidak ada. */
    public int search(String plat) {
        if (plat == null || isEmpty()) {
            return -1;
        }
        int kiri = 0, kanan = jumlah - 1;
        while (kiri <= kanan) {
            int tengah = (kiri + kanan) / 2;
            int banding = data[tengah].getPlatNomor().compareToIgnoreCase(plat.trim());
            if (banding == 0) return tengah;
            if (banding < 0) kiri = tengah + 1;
            else kanan = tengah - 1;
        }
        return -1;
    }

    /** Ambil elemen pada indeks; null jika indeks tidak valid. */
    public Kendaraan get(int index) {
        if (index < 0 || index >= jumlah) {
            System.out.println("Error: indeks " + index + " di luar batas (0-" + (jumlah - 1) + ").");
            return null;
        }
        return data[index];
    }

    public void tampilkan() {
        if (isEmpty()) {
            System.out.println("(Daftar kendaraan kosong)");
            return;
        }
        for (int i = 0; i < jumlah; i++) {
            System.out.print((i + 1) + ". ");
            data[i].tampilkanInfo();
        }
    }
}
