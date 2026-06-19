# PantauSiKecil Kotlin - Tahap 2 API

Project ini adalah rekonstruksi PantauSiKecil dalam Kotlin Native Android + Jetpack Compose.
Tahap 2 menambahkan koneksi API berdasarkan `API_DOCUMENTATION.md`.

## Yang sudah ditambahkan

- Retrofit + OkHttp + Gson converter.
- Internet permission dan cleartext HTTP untuk backend lokal.
- JWT Bearer token otomatis melalui `AuthInterceptor`.
- Penyimpanan token sederhana menggunakan `SharedPreferences` di `TokenStore`.
- Repository untuk Auth, Anak, Pemeriksaan, Jadwal, Recap, dan Laporan.
- Login screen sudah memanggil `POST /auth/login`.
- Data dashboard mengambil `GET /anak` dan `GET /jadwal`.
- Tambah anak memanggil `POST /anak`.
- Tambah pemeriksaan memanggil `POST /anak/{anakId}/pemeriksaan`.
- Tambah/edit/hapus jadwal memanggil endpoint `/jadwal`.

## Base URL

Default base URL berada di:

```kotlin
app/src/main/java/com/pantausikecil/network/ApiConfig.kt
```

Untuk Android Emulator gunakan:

```kotlin
http://10.0.2.2:3000/
```

Untuk HP fisik, ganti dengan IP laptop/server backend, contoh:

```kotlin
http://192.168.1.10:3000/
```

Pastikan backend Express berjalan:

```bash
npm run dev
# atau
npm start
```

## Catatan penting

- Jika backend belum berjalan, aplikasi akan menampilkan mock data agar UI tetap bisa diuji.
- Input tanggal API sebaiknya memakai format `yyyy-MM-dd`, misalnya `2022-01-13`.
- Field NIK wajib 16 digit sesuai validasi backend.
- Endpoint pemeriksaan memerlukan webhook n8n aktif. Jika `N8N_WEBHOOK_GROWTH_URL` belum aktif, backend dapat mengembalikan error 502.

## File penting

- `network/PantauApiService.kt`: daftar semua endpoint.
- `network/ApiModels.kt`: request/response DTO.
- `network/ApiMappers.kt`: mapper DTO ke model UI.
- `data/repository/*Repository.kt`: lapisan akses data.
- `MainActivity.kt`: integrasi awal API ke UI.

## Update Login UI Fixed

Versi ini menyesuaikan ulang `LoginScreen.kt` agar lebih mendekati referensi UI: judul besar di bagian atas, card login putih dengan rounded corner besar, input abu-abu filled, serta tombol hijau lebar.

Jika build masih membaca cache lama, jalankan:

```bash
./gradlew --stop
rm -rf .gradle build app/build
./gradlew assembleDebug --no-configuration-cache
```
# PantauSiKecil_app
