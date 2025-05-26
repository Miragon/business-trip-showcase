# Business Trip Showcase

## Getting started

1. Start the backend
   ```shell
   cd process-application
   export $(grep -v '^#' src/main/resources/.env | xargs) && mvn spring-boot:run
   ```

2. Start the frontend
   ```shell
   cd frontend
   yarn
   yarn serve
   ```

## Prozess

![dienstreisenantrag.svg](docs/Dienstreisenantrag.svg)

## Architektur

![architecture.png](docs/architecture.excalidraw.png)

## Ablauf

```mermaid
sequenceDiagram
    Antragsteller ->> Applikation: Starte eine Prozessinstanz
    Applikation ->> Engine: Starte Prozess "Dienstreisenantrag"
    Engine -->> Applikation: Prozess gestartet
    Engine ->>+ Applikation: User Task erstellt
    Applikation ->>- Genehmiger: E-Mail mit Link versendet
    Genehmiger ->> Genehmiger: Link öffnen
    Genehmiger ->>+ Applikation: Hole Formular mit Daten
    Applikation ->>+ Engine: Hole Prozess Daten
    Engine -->>- Applikation: Prozess Daten
    Applikation -->>- Genehmiger: Formular mit Daten
    Genehmiger ->> Applikation: User Task abschließen
    Applikation ->> Engine: User Task abschließen
    Engine -->> Applikation: User Task abgeschlossen
```

## Technologien

* Camunda 7 / 8
* [Process Engine API](https://github.com/bpm-crafters/process-engine-api)
* [Formcentric](https://formcentric.com/de/)
* [Novu](https://docs.novu.co/platform/overview)
* [JSON Forms](https://jsonforms.io/docs/)
