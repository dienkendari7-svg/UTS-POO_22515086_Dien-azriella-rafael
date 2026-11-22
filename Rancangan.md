1. Class Kendaraan

Atribut

- platNomor : String adalah kunci unik dan kunci pengurutan ADT. Tidak boleh kosong.
- merek : String, tidak boleh kosong.
- tipe : String, model kendaraan, tidak boleh kosong.
- tahun : int, tahun produksi, wajib antara 1980 dan tahun berjalan.
- sedangDiservis : boolean, bernilai true jika ada servis aktif. Awalnya false.

Method

+ Kendaraan() adalah konstruktor default dengan nilai awal kosong.
+ Kendaraan(platNomor: String, merek: String, tipe: String, tahun: int) adalah konstruktor berparameter yang memakai this dan setter.
+ getPlatNomor(): String, getMerek(): String, getTipe(): String, getTahun(): int, isSedangDiservis(): boolean adalah getter.
+ setPlatNomor(String), setMerek(String), setTipe(String), setTahun(int) adalah setter dengan validasi.
+ mulaiServis(): void mengubah sedangDiservis menjadi true.
+ selesaiServis(): void mengubah sedangDiservis menjadi false.
+ hitungUsia(tahunSekarang: int): int mengembalikan tahunSekarang - tahun.
+ tampilkanInfo(): void mencetak data kendaraan dan statusnya.

2. Class Mekanik

Atribut

- idMekanik : String adalah kunci unik, tidak boleh kosong.
- nama : String, tidak boleh kosong.
- spesialisasi : String, misalnya mesin, kelistrikan, atau body.
- sibuk : boolean, bernilai true jika sedang menangani servis aktif.
- servisAktif : Servis, referensi ke servis yang sedang ditangani. Bernilai null jika tidak ada.

Method

+ Mekanik() adalah konstruktor default.
+ Mekanik(idMekanik: String, nama: String, spesialisasi: String) adalah konstruktor berparameter.
+ getIdMekanik(), getNama(), getSpesialisasi(), isSibuk(), getServisAktif() adalah getter.
+ setNama(String), setSpesialisasi(String) adalah setter dengan validasi.
+ isTersedia(): boolean mengembalikan !sibuk.
+ tugaskan(servis: Servis): boolean menegakkan aturan satu servis aktif. Jika mekanik sedang sibuk, program menampilkan pesan error dan mengembalikan false.
+ selesaikanTugas(): void mengatur sibuk = false dan servisAktif = null.
+ tampilkanInfo(): void mencetak data mekanik dan statusnya.

3. Class Servis

Atribut

- kodeServis : String dibuat otomatis, misalnya SRV-001.
- kendaraan : Kendaraan, asosiasi ke kendaraan yang diservis.
- mekanik : Mekanik, asosiasi ke mekanik penangan.
- keluhan : String, deskripsi kerusakan, tidak boleh kosong.
- biayaJasa : double, tidak boleh negatif.
- biayaSukuCadang : double, tidak boleh negatif.
- status : String, bernilai AKTIF atau SELESAI (konstanta STATUS_AKTIF dan STATUS_SELESAI).

Method

+ Servis() adalah konstruktor default.
+ Servis(kodeServis: String, kendaraan: Kendaraan, mekanik: Mekanik, keluhan: String, biayaJasa: double, biayaSukuCadang: double) adalah konstruktor berparameter dengan status awal AKTIF.
+ getKodeServis(), getKendaraan(), getMekanik(), getKeluhan(), getBiayaJasa(), getBiayaSukuCadang(), getStatus() adalah getter.
+ setBiayaJasa(double), setBiayaSukuCadang(double), setKeluhan(String) adalah setter yang menolak nilai negatif atau kosong.
+ hitungBiayaTotal(): double mengembalikan biayaJasa + biayaSukuCadang.
+ selesaikan(): void mengubah status menjadi SELESAI dan membebaskan kendaraan serta mekanik.
+ tampilkanInfo(): void mencetak rincian servis dan biaya total.

4. Class Bengkel

Atribut

- nama : String, nama bengkel.
- alamat : String, alamat bengkel.
- totalPendapatan : double, akumulasi biaya servis yang sudah selesai.
- counterServis : int, pembuat nomor kode servis otomatis.
- daftarMekanik : ArrayList<Mekanik>, agregasi 1..* mekanik.
- daftarServis : ArrayList<Servis>, komposisi 0..* servis.
- daftarKendaraan : DaftarKendaraanTerurut, komposisi ADT Sorted List.

Method

+ Bengkel() adalah konstruktor default.
+ Bengkel(nama: String, alamat: String, kapasitas: int) adalah konstruktor berparameter yang membuat ADT dengan kapasitas tertentu.
+ getNama(), getAlamat(), getTotalPendapatan() adalah getter.
+ setNama(String), setAlamat(String) adalah setter dengan validasi.
+ tambahKendaraan(k: Kendaraan): boolean memanggil insert ADT dan menangani kondisi penuh serta duplikat.
+ tambahMekanik(m: Mekanik): boolean menolak ID duplikat.
+ buatServis(plat: String, idMekanik: String, keluhan: String, jasa: double, suku: double): boolean memeriksa kendaraan ada dan tidak sedang diservis, memeriksa mekanik tersedia, lalu membuat objek Servis.
+ selesaikanServis(kode: String): boolean menyelesaikan servis dan menambah totalPendapatan.
+ cariKendaraan(plat: String): Kendaraan memakai binary search lewat ADT. Mengembalikan null jika tidak ketemu.
+ cariMekanik(id: String): Mekanik memakai pencarian linear. Mengembalikan null jika tidak ketemu.
+ tampilkanSemuaMekanik(): void dan tampilkanSemuaServis(): void mencetak seluruh data masing-masing.
+ tampilkanLaporan(): void menampilkan jumlah kendaraan, mekanik, servis aktif dan selesai, serta total pendapatan.

5. Class DaftarKendaraanTerurut (ADT Sorted List)

Atribut

- data : Kendaraan[], array penyimpan elemen yang terurut menurut platNomor.
- jumlah : int, banyak elemen saat ini.
- KAPASITAS : int (final), kapasitas maksimum array.

Method

+ DaftarKendaraanTerurut(kapasitas: int) adalah konstruktor. Kapasitas harus lebih dari 0.
+ insert(k: Kendaraan): boolean menyisipkan elemen tetap terurut dan menolak jika penuh atau plat duplikat.
+ remove(plat: String): boolean menghapus elemen dan menggeser sisanya. Menangani kondisi kosong dan tidak ditemukan.
+ search(plat: String): int memakai binary search. Mengembalikan indeks, atau -1 jika tidak ada.
+ get(index: int): Kendaraan mengembalikan null dan menampilkan pesan jika indeks di luar batas.
+ tampilkan(): void mencetak semua elemen secara terurut.
+ size(): int mengembalikan jumlah.
+ isEmpty(): boolean mengembalikan jumlah == 0.
+ isFull(): boolean mengembalikan jumlah == KAPASITAS.

6. Class Main
+ main(args: String[]): void menjalankan loop menu sampai pilihan 7 (keluar).
- tampilkanMenu(): void mencetak menu.
- bacaInt(prompt: String): int membaca angka dengan validasi dan mengulang jika salah.
- bacaDouble(prompt: String): double sama seperti bacaInt, untuk bilangan desimal.
- bacaString(prompt: String): String menolak input kosong.
- menuKendaraan(), menuMekanik(), menuServis(), menuCari(), menuAdt(), menuLaporan() menangani tiap pilihan menu.
  
