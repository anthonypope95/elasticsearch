# Confronto performance tra Elasticsearch e Splunk

## Riferimenti
- [Guida setup Elasticsearch-Kibana-Logstash](https://github.com/anthonypope95/elasticsearch/blob/master/ElasticGuide.md)

- [Installazione Splunk su CentOS](https://www.bitsioinc.com/tutorials/install-splunk-centos/)

---
## Fase Pre-Test

#### Sono stati caricati all'interno di Elasticsearch degli oggetti JSON prelevati da un file data.json
#### Di seguito viene riportato il contenuto del file di configurazione di Logstash utilizzato per il caricamento dei dati all'interno di Elasticsearch.

```
input {
    
    file {
        type => "json"
        path => "/home/data.json"
        start_position => "beginning"
        codec => "json"
    }
}

filter {
    
    json {
        source => "message"
    }
}

output {
    
    stdout { codec => rubydebug }

    elasticsearch{      
        hosts => ["ip_elastic_host:9200"]
        index => ["main"]
        document_type => "json"
    }
}

```

#### Il medesimo file data.json è stato caricato all'interno di Splunk utilizzando l'interfaccia grafica presente all'indirizzo http://ip_splunk_host:8000


### Totale numero eventi presenti nel file data.json : 62.206

### Nota: i test delle performance sono stati eseguiti utilizzando (mantenendo attivo) un servizio per volta (Elasticsearch e Kibana / Splunk)


---

## Testing

### Test 1

### Query Splunk utilizzata: 
> index="main" port=8080
#### Risultato: This search has completed and has returned 242 risultati by scanning 284 eventi in 0,08 seconds

### Query Elasticsearch utilizzata: 

>_index:"main" AND port:8080


#### Risultato:
#### The total time the request took: 688 ms = 0.688 s
#### Query time: 220 ms = 0.220 s
#### Total hits: 242

---

### Test 2

### Query Splunk utilizzata: 
> index="main" port=80 AND pluginname="Nessus SYN scanner"
#### Risultato: This search has completed and has returned 776 risultati by scanning 1.085 eventi in 0,154 seconds

### Query Elasticsearch utilizzata:

> _index:"main" AND port:80 AND pluginname:"Nessus SYN scanner"

#### Risultato:
#### The total time the request took: 1235 ms = 1.235 s
#### Query time: 639 ms = 0.639 s
#### Total hits: 776

---

### Test 3

### Query Splunk utilizzata: 
> index="main" AND connectorname=NESSUS_B AND protocol=TCP AND port=80

#### Risultato: This search has completed and has returned 631 risultati by scanning 866 eventi in 0,144 seconds

### Query Elasticsearch utilizzata:
>_index:"main" AND connectorname:NESSUS_B AND protocol:TCP AND port:80

#### Risultato:
#### The total time the request took: 991 ms = 0.991 s
#### Query time: 524 ms = 0.524 s
#### Total hits: 631

---

### Test 4 


### Query Splunk utilizzata: 
> index="main" AND protocol=UDP AND connectorname=TenableAlfa

#### Risultato: This search has completed and has returned 546 risultati by scanning 546 eventi in 0,125 seconds


### Query Elasticsearch utilizzata:
> _index:"main" AND protocol:UDP AND connectorname:TenableAlfa

#### Risultato:
#### The total time the request took: 734 ms = 0.734 s
#### Query time: 246 ms = 0.246 s
#### Total hits: 546


---