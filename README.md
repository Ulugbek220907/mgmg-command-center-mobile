# Primus Command — MGMG Command Center Mobile

> **MGMG AI Агентлар Тизими ва Мобил Бошқарув Маркази**  
> Kotlin Multiplatform (Compose Multiplatform) loyihasi: Android, iOS va Desktop (JVM) uchun backend'siz (in-memory mock repository bilan) to'liq tayyorlangan.

---

## 🌟 Loyiha haqida

Ushbu dastur **ЭМЖИЕМ (Primus Laundry)** biznesini avtomatlashtirish va tizimlashtirish bo'yicha ikkita asosiy hujjat asosida yaratilgan:
1. `ЭМЖИЕМ_AI_Агентлар_Тизими.docx` — 21 ta AI agent, audit tahlili, 90 kunlik reja, 3 xil byudjet, ortiqcha agentlar va formulalar.
2. `ЭМЖИЕМ_AI_Агентлар_Трекери.xlsx` — 7 ta varaq (1-ЙЎРИҚНОМА, 2-АГЕНТЛАР, 3-ХИСОБ, 4-БЮДЖЕТ, 5-РЕЖА, 6-КЕРАКСИЗ, 7-ДАШБОРД).

### 🎨 Dizayn tizimi: Terra ("Rooted Warmth")
- **Primary (`#4a7c59`):** O'rmon yashili (Forest green).
- **Background (`#faf6f0`):** Issiq krem (Warm cream).
- **Tertiary (`#705c30`):** Issiq kahrabo (Warm amber).
- **Tipografiya:** Literata (serif sarlavhalar), Nunito Sans (asosiy matn va yorliqlar), JetBrains Mono (kodlar va ID lar).
- **Kompensatsiya:** Yumshoq burchaklar (12–16px), tabiiy soya va chegaralar.

---

## 📱 Dastur Ekranlari va Funksionallik

1. **Kirish (Auth & Invite Code Screen):**
   - Bir martalik taklif kodi (`PRM-XXXX-XXXX`) orqali kirish.
   - Telegram MGMG Admin boti (`@mgmg_admin_bot`) integratsiyasi.
   - 15 daqiqalik amal qilish taymeri va FaceID / Biometrik kalit bog'lash.
   - SSL v3 shifrlash va xavfsizlik protokoli.

2. **Bosh sahifa (Home Screen):**
   - Xush kelibsiz vidjeti (xodim: Alisher Po'latov, filial: Primus Laundry, sana: Asia/Tashkent).
   - Bugungi hisobot holati (16:00 dan 00:00 gacha faol qabul oralig'i, progress bar, 17:00 eslatmasi).
   - AI Nazorat Tizimi avtomat bildirishnomasi.
   - Ruxsatnomalar (SOP) tezkor holat kartochkasi.
   - Direktor brifi va ko'rsatmalari vidjeti.
   - 21 AI Agent Trekeri va 5 Raqam Boshqaruv paneliga tezkor tugmalar.

3. **Kunlik hisobot (Daily Report & History Screen):**
   - Faol topshirish qoidalari (16:00 – 00:00).
   - Kamida 50 ta belgi validatsiyasi bilan matn kiritish maydoni (1000 belgigacha hisoblagich).
   - **AI aniqlashtiruvchi savoli kartochkasi:** Faqat 1 marta beriladigan savol va unga tezkor javob kiritish.
   - Oxirgi 14 kunlik hisobotlar tarixi (Qabul qilindi, AI bilan to‘ldirildi, Topshirilmadi).

4. **Yozma ruxsatnoma arizasi (EMJ-SOP-ADM-01 Request Wizard):**
   - 4 bosqichli vizard (1. Asosiy, 2. Sabab, 3. Muddat, 4. Ko‘rish).
   - Ruxsatnoma toifalari: Xizmat safari, Ta'til / Ruxsat, Moddiy javobgarlik / Xarajat, Boshqa.
   - Sabab va mufassal asos kiritish maydoni.
   - **AI tekshiruvi va rasmiy o'zbek kirill yozuviga avtomatik o'girish** (Buyruq ilovasi uchun «...» rasmiy matn).
   - O‘rinbosar xodim (Jamshid Saidov) va limitlar.
   - O'z-o'ziga ruxsat berish taqiqlanishi haqida ogohlantirish.

5. **Ruxsatnomani ko'rib chiqish (SOP Detail Screen):**
   - To'liq ariza tafsilotlari, xodim pasporti, muddat va smeta.
   - Tayyor `.docx` hujjati harakati (Ko'rish va Yuklab olish).
   - **Direktor qarori (4 ta standart natija):**
     1. Tasdiqlash (Qabul qilindi)
     2. Shartli tasdiqlash
     3. Qo‘shimcha ma’lumot so‘rash
     4. Rad etish

6. **Direktor qarori modali (Decision Bottom Sheet):**
   - 4 natijali radio matrisa.
   - Majburiy qaror sharti yoki ko'rsatmasi kiritish maydoni.
   - Tayyor shablon ko'rsatmalar chiplari (Xarajat limiti, Hisobot 3 kunda, O'rinbosar bilan muvofiqlashtirish).
   - EMJ-SOP-ADM-01 .docx hujjatining "Rahbar xulosasi" bandiga avtomatik biriktirish.
   - Raqamli imzo muhri (Operatsion Direktor • ID: `884-OD`).

7. **21 AI Agentlar Trekeri (Agents Tracker Screen):**
   - 8 ta funksional blok (0. Инфратузилма, A. Назорат, Б. Молия, В. Савдо, Г. Операция, Д. Одамлар, Е. Маркетинг, Ж. Шахсий).
   - Shoshilinchlik filtri: Критик (≥18), Шошилинч (≥12), Оддий (≥4.8).
   - Har bir agent bo'yicha to'liq formula: `Балл = Пул таъсири (1–10) × Эҳтимол (0.1–1.0) × Тўсиқ коэф. (1–3)`.
   - **Олтин қоида назорати:** Бир вақтда фақат 2 та агент қурилиши мумкин. 3-агентни бошлаш тизим томонидан чекланади!
   - Har bir agentning nima qilishi, nima uchun kerakligi (audit dalili), platformasi va mas'uli.

8. **5 Raqam Rahbar Dashborti & Moliya (Executive Dashboard):**
   - 1. Касса қолдиғи (184.2 млн сўм).
   - 2. Кечаги сотув (42.5 млн сўм).
   - 3. Захира қиймати ($486,733 — 256 кунлик мол).
   - 4. Мижоз қарзи (215.8 млн сўм).
   - 5. Бугунги тўловлар (38.1 млн сўм).
   - **A2 Qat'iy xavf qoidasi:** «Захира ошиб, касса тушса -> барча янги буюртма тўхтайди.»
   - Interaktiv reallashish koeffitsiyenti slaydri (0.1 dan 1.0 gacha) va dinamik ROI (9.5x) hamda o'zini qoplash (1.1 oy) hisoblagichi.
   - 90 kunlik bosqichma-bosqich reja (6 bosqich, 138 soat, Sifat I koeffitsiyenti).
   - Keraksiz agentlar bo'limi (X1, X2, X3, X4 — tejaladigan $9K-$12K/yil va 60 soat).

9. **Sozlamalar va Profil (Settings & Profile Screen):**
   - Alisher Po'latov pasporti (EMJ-204, Toshkent Markaz, Faol xodim).
   - Push bildirishnomalar (16:00 kunlik hisobot, 17:00 eslatma, SOP holatlari).
   - Expo Push faol • iPhone 15 Pro statusi.
   - Dastur versiyasi (v1.0.4 Build 42), til va vaqt mintaqasi sozlamalari.
   - Xavfsizlik seansi, Telegram (@alisher_mgmg) va Tizimdan chiqish (Log out).

---

## 🛠️ Loyihani qurish va ishga tushirish (Android Studio'siz)

Loyiha buyruqlar satri (CLI) orqali to'liq ishlaydi.

### Talablar:
- Java 17 LTS o'rnatilgan bo'lishi kifoya (`java -version`).
- Android Studio yoki qo'shimcha og'ir dasturlar talab qilinmaydi!

### 1. Testlarni ishga tushirish:
```bash
./gradlew test
# Windows da:
gradlew.bat test
```

### 2. Desktop ilovasini ishga tushirish:
```bash
./gradlew desktopRun
# Windows da:
gradlew.bat desktopRun
```

### 3. Butun loyihani yig'ish (Build):
```bash
./gradlew build
# Windows da:
gradlew.bat build
```

---

## 🚀 GitHub Actions va iOS ga eksport qilish

Loyiha `.github/workflows/build.yml` fayli orqali avtomatlashtirilgan CI/CD ga ega:

1. **Test & Verification:** Linux serverida barcha testlarni o'tkazadi.
2. **Android APK:** Android SDK muhitida `composeApp-debug.apk` yig'iladi va yuklab olish uchun saqlanadi.
3. **iOS Export:** GitHub macOS runnerida (`macos-14` / Apple Silicon) Xcode orqali iOS Framework (`ComposeApp.framework`) yig'iladi va iOS loyihasiga qo'shish uchun tayyor holda eksport qilinadi.

Loyihani GitHub repozitoriyasiga `push` qilishingiz bilanoq ushbu jarayonlar avtomatik tarzda ishga tushadi!
