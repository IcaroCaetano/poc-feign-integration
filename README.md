# 🧩 POC - Feign Client Integration with Resilience4j

## 📖 Overview
This Proof of Concept (**POC**) demonstrates how to integrate an external REST API using **Spring Cloud OpenFeign**, apply **resilience patterns** with **Resilience4j**, and handle custom error decoding in a clean and maintainable way.

The POC consumes the public [ViaCEP API](https://viacep.com.br/), which provides address data for Brazilian postal codes (CEP).

---

## 🧠 Objectives
- Demonstrate how to use **Feign Client** to consume external APIs.
- Implement **Resilience4j** patterns such as:
  - 🔁 Retry
  - 🚦 Circuit Breaker
- Implement **custom error handling** for HTTP responses.
- Write **integration tests** using **WireMock** and **MockMvc**.

---

## ⚙️ Tech Stack

| Component | Description |
|------------|-------------|
| **Java 17+** | Programming language |
| **Spring Boot 3** | Framework for building the REST API |
| **Spring Cloud OpenFeign** | HTTP client for declarative API calls |
| **Resilience4j** | Retry and Circuit Breaker management |
| **WireMock** | Mocking external APIs during tests |
| **MockMvc** | Spring test framework for REST endpoints |
| **Gradle** | Build and dependency management |

---

## 🏗️ Architecture

````

poc-feign-integration/
│
├── src/main/java/com/example/pocfeignintegration/
│   ├── controller/
│   │   └── AddressController.java
│   ├── client/
│   │   └── ViaCepClient.java
│   ├── config/
│   │   └── FeignConfig.java
│   ├── dto/
│   │   └── AddressResponse.java
│   ├── service/
│   │   └── AddressService.java
│   ├── exception/
│   │   ├── CustomErrorDecoder.java
│   │   ├── ApiException.java
│   │   └── ErrorResponse.java
│   └── PocFeignIntegrationApplication.java
│
├── src/test/java/com/example/pocfeignintegration/
│   ├── integration/
│   │   └── AddressControllerTest.java
│   └── resources/
│       └── mappings/
│
└── build.gradle
````

````

---

## 🚀 Running the Project

### **1️⃣ Clone the Repository**
```bash
git clone https://github.com/yourusername/poc-feign-resilient-integration.git
cd poc-feign-resilient-integration

````
## 2️⃣ Build the Project

````
./gradlew clean build

````

## 3️⃣ Run the Application

````
./gradlew bootRun

````

## 4️⃣ Test the Endpoint

Open your browser or use curl:

````
curl http://localhost:8080/address/01001000

````

Expected response:

````
{
  "cep": "01001-000",
  "logradouro": "Praça da Sé",
  "bairro": "Sé",
  "localidade": "São Paulo",
  "uf": "SP"
}

````

## 🧪 Running Tests

The integration tests use WireMock to simulate the ViaCEP API.

````
./gradlew test

````

## ⚡ Resilience4j Configuration

````
resilience4j:
  retry:
    instances:
      viaCepRetry:
        maxAttempts: 3
        waitDuration: 500ms

  circuitbreaker:
    instances:
      viaCepCircuitBreaker:
        slidingWindowSize: 5
        failureRateThreshold: 50
        waitDurationInOpenState: 5s

````
