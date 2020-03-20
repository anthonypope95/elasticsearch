# Test Plan

## Steps

1. Installazione di Splunk, Elasticsearch e DB relazionale

_L’installazione dei seguenti sistemi deve essere fatta sulla stessa macchina o su macchine separate che hanno le medesime caratteristiche software/hardware?_

2. Prelievo della collezione di dati in formato JSON dal sistema Splunk presente all’indirizzo 10.10.10.95:8000

3. Caricamento dei dati (prelevati nello step precedente) nei sistemi Splunk, Elasticsearch e DB relazionale

_Il caricamento in Elasticsearch viene fatto mediante Logstash_

_Nel caso in cui i dati debbano essere caricati nel DB relazionale, come li carichiamo visto che il formato usato è JSON?_

4. Scelta delle interrogazioni da effettuare su tutti e tre i sistemi (vanno effettuate le medesime interrogazioni su tutti e tre i sistemi).

_Tenere attivo durante i test un servizio per volta_

5. Effettuare interrogazioni sui sistemi.

_L’ interrogazione su Elasticsearch viene fatta attraverso l’utilizzo di Kibana_

6. Confronto dei risultati ottenuti dalle interrogazioni mediante query (confronto tempi di risposta).

---

## Suggerimenti 

- Sarebbe interessante conoscere oltre alle performance temporali anche il consumo di risorse fisiche della macchina: CPU, RAM ecc….

_In Elasticsearch questa cosa può essere fatta usando il servizio Metricbeat_


### Riferimenti
- [Install Metricbeat](https://www.elastic.co/guide/en/elastic-stack-get-started/current/get-started-elastic-stack.html#install-metricbeat)

- [Ship system metrics to Elasticsearch](https://www.elastic.co/guide/en/elastic-stack-get-started/current/get-started-elastic-stack.html#ship-system-logs)
