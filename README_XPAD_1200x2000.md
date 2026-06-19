# PantauSiKecil - Xpad 20 Pro 1200x2000

Project ini sudah disesuaikan untuk target layar tablet portrait Infinix XPAD 20 Pro:

- Resolusi fisik: 1200 x 2000 px portrait
- Target Compose preview/logical layout: sekitar 800 x 1333 dp
- Orientasi activity: portrait
- Komponen UI diperbesar untuk tablet: menu tile, card, button, text field, dashboard summary, bottom navigation, dialog, dan chart placeholder.

## Build debug

```bash
./gradlew --stop
./gradlew clean
./gradlew assembleDebug --no-configuration-cache
```

APK debug akan berada di:

```text
app/build/outputs/apk/debug/app-debug.apk
```

## Catatan

Android tidak mengunci UI berdasarkan pixel langsung. Jetpack Compose memakai dp, sehingga project ini dibuat responsif dengan breakpoint tablet agar tampil proporsional pada layar 1200 x 2000 px.
