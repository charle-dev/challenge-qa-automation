# Accenture QA Automation Challenge — README

Este repositório contém a suíte de testes **Cucumber + Selenium** em **Java 21** executada via **JUnit Platform**.  


## 🔧 Stack e principais dependências

- **Java:** 21 (`<maven.compiler.release>21</maven.compiler.release>`).
- **Build/Test:** Maven 3.9+ com **JUnit Platform** e **Cucumber**  
  `io.cucumber:cucumber-java`, `io.cucumber:cucumber-core`, `io.cucumber:cucumber-junit-platform-engine`  
- **Browser/UI:** `org.seleniumhq.selenium:selenium-java`
- **Driver:** `io.github.bonigarcia:webdrivermanager` (dispensa baixar chromedriver manualmente)
- **Logs:** `org.slf4j:slf4j-simple`

Arquitetura de testes:
- **Page Objects** (`com.accenture.challenge.pages`) isolam seletores e ações.
- **Steps** (`com.accenture.challenge.steps`) mapeiam Gherkin → Java.
- **Utilitários** (`com.accenture.challenge.Ui`) com waits robustos, cliques resilientes, scrolls, screenshots.
- **DriverFactory** com `ThreadLocal<WebDriver>` (suporte a paralelismo futuro).


## 🗂️ Estrutura relevante

accenture-qa-automation-fixed-v15/
└── accenture-qa-automation/
    ├── pom.xml
    ├── src/
    │   └── test/
    │       ├── java/com/accenture/challenge/
    │       │   ├── RunCucumberTest.java      # <— NOVO runner JUnit Platform
    │       │   ├── CucumberCli.java          # runner via main class (IDE/Debug)
    │       │   ├── DriverFactory.java
    │       │   ├── Ui.java
    │       │   ├── pages/…
    │       │   └── steps/…
    │       └── resources/features/
    │           └── ui/*.feature              # cenários @ui
    └── target/cucumber-report.html           # relatório HTML gerado


## ✅ Pré-requisitos

- **Java 21** (`java -version` → 21.x)
- **Maven 3.9+** (`mvn -v`)
- **Google Chrome** instalado e atualizado
- **Acesso à internet** (WebDriverManager baixa o driver)

Sistemas: Windows, macOS, Linux (validados com Chrome).


## ▶️ Como executar os testes

mvn clean test


## 📄 Relatórios

Após a execução, abra: accenture-qa-automation/target/cucumber-report.html


## Convenções e práticas

- **POM (Page Object Model):** separa responsabilidades e facilita manutenção.
- **Sincronização explícita:** `WebDriverWait`/`ExpectedConditions` + utilitários `Ui` (flakiness ↓).
- **Cliques/scrolls robustos:** helpers no `Ui` para lidar com overlays/banners do DemoQA.
- **Paralelismo futuro:** `DriverFactory` com `ThreadLocal` já pronto para evoluir.
- **Tags por contexto:** `@ui`, `@api`, `@wip`.

## Créditos

- **Autor:** *Carlos Alberto Bezerra Filho*
- **Linkedin** https://www.linkedin.com/in/carlos-a-bezerra/
