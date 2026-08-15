# Expense Tracker API

Kişisel harcamaları kategori ve bütçe bazında takip etmeyi sağlayan, JWT tabanlı kimlik doğrulama ile korunan bir REST API.

## Özellikler

- **JWT Authentication** — kullanıcı kayıt/giriş, stateless token tabanlı kimlik doğrulama
- **Harcama Yönetimi (CRUD)** — her kullanıcı yalnızca kendi harcamalarını görüntüleyebilir/yönetebilir
- **Kategori Bazlı Bütçe Limiti** — aylık bütçe tanımlama ve aşım kontrolü
- **Kategori/Ay Bazlı Özet Raporlama** — harcamaların gruplu ve toplu görünümü
- **DTO / Entity Ayrımı** — MapStruct ile otomatik mapping, entity'lerin dışarı sızdırılmaması
- **Katmanlı Mimari** — controller / service / repository / dto / mapper / security ayrımı

## Kullanılan Teknolojiler

| Katman | Teknoloji |
|---|---|
| Dil / Framework | Java 21, Spring Boot 3.4 |
| Veri Erişimi | Spring Data JPA, Hibernate |
| Veritabanı | H2 (in-memory, geliştirme ortamı) |
| Güvenlik | Spring Security, JJWT (JSON Web Token) |
| Mapping | MapStruct |
| Test | JUnit 5, Mockito |
| Build | Maven |

## Mimari

```
com.example.expensetracker
├── config          # Genel konfigürasyonlar
├── controller       # REST endpoint'leri
├── dto
│   ├── request      # Client'tan gelen veri sözleşmeleri
│   └── response     # Client'a dönen veri sözleşmeleri
├── entity           # JPA entity'leri
├── exception        # Merkezi hata yönetimi
├── mapper           # MapStruct interface'leri (Entity <-> DTO)
├── repository       # Spring Data JPA repository'leri
├── security         # JWT filter, JWT util, Security config
└── service          # İş mantığı
```

## Kurulum ve Çalıştırma

```bash
git clone https://github.com/<kullanici-adin>/expense-tracker.git
cd expense-tracker
mvn clean install
mvn spring-boot:run
```

Uygulama varsayılan olarak `http://localhost:8080` üzerinde ayağa kalkar.

H2 konsoluna `http://localhost:8080/h2-console` adresinden erişilebilir (JDBC URL: `jdbc:h2:mem:expensedb`).

## API Uç Noktaları

### Authentication
| Metod | Endpoint | Açıklama |
|---|---|---|
| POST | `/api/auth/register` | Yeni kullanıcı kaydı |
| POST | `/api/auth/login` | Giriş yapar, JWT token döner |

### Expenses (Authentication gerektirir)
| Metod | Endpoint | Açıklama |
|---|---|---|
| GET | `/api/expenses` | Giriş yapan kullanıcının tüm harcamaları |
| POST | `/api/expenses` | Yeni harcama oluşturur |
| GET | `/api/expenses/summary` | Kategori/ay bazlı özet rapor |

### Budgets (Authentication gerektirir)
| Metod | Endpoint | Açıklama |
|---|---|---|
| POST | `/api/budgets` | Kategori bazlı aylık bütçe limiti tanımlar |
| GET | `/api/budgets/status` | Bütçe aşım durumu kontrolü |

Tüm korumalı endpoint'ler `Authorization: Bearer <token>` header'ı bekler.

## Örnek İstek

**Kayıt:**
```json
POST /api/auth/register
{
  "username": "merve",
  "password": "guclu-bir-sifre"
}
```

**Giriş:**
```json
POST /api/auth/login
{
  "username": "merve",
  "password": "guclu-bir-sifre"
}
```
Yanıt:
```json
{
  "token": "eyJhbGciOiJIUzM4NCJ9..."
}
```

**Harcama oluşturma:**
```json
POST /api/expenses
Authorization: Bearer eyJhbGciOiJIUzM4NCJ9...

{
  "description": "Market alışverişi",
  "amount": 250.50,
  "expenseDate": "2026-08-14",
  "categoryId": 1
}
```

## Testler

```bash
mvn test
```

Servis katmanı Mockito ile izole edilerek unit test, repository/security akışları entegrasyon testleriyle kontrol edilir.

## Geliştirme Notları

- Şifreler `BCryptPasswordEncoder` ile hash'lenir, hiçbir zaman plain text saklanmaz.
- JWT, stateless authentication sağlar — sunucu tarafında session tutulmaz.
- `@OneToMany`/`@ManyToOne` ilişkilerinde N+1 sorununu önlemek için gerekli yerlerde `JOIN FETCH` / `@EntityGraph` kullanılmıştır.
- DTO/Entity ayrımı, iç veri modelinin dışarıya sızmasını engeller ve API sözleşmesini entity değişikliklerinden izole eder.
