# Elasticsearch Security Guide


## Prerequisiti

- Inserire delle policy sul servizio firewalld che permettono la connessione al nodo Elasticsearch

---


## Set Built-in Account Passwords

Avviare il servizio Elasticsearch

> ``systemctl start elasticsearch ``

Eseguire il setup delle password dei seguenti servizi:  elastic, kibana, logstash_system, beats_system, apm_system, and remote_monitoring.

> ``/usr/share/elasticsearch/bin/elasticsearch-setup-passwords interactive``

Recarsi nel file /etc/kibana/kibana.yml e inserire i seguenti parametri di configurazione:


```
elasticsearch.user: kibana
elasticsearch.password: <your password here>
```

Accedere a kibana dal browser: http://10.10.10.52:5601/
utilizzando le credenziali dell'utente elastic

Recarsi nella sezione security di kibana per assegnare i ruoli agli utenti


![Screenshot](images/Cattura1.png)

---

## Generazione dei certificati

Inserire gli IP dei nodi del cluster nel file di configurazione /etc/elasticsearch/elasticsearch.yml

creare il file cert-gen.yml per la generazione dei certificati

> ``sudo vi ~/cert-gen.yml``

Contenuto da inserire: 

```
instances:
  - name: "elastic1" 
    dns:
      - "elastic1"
    ip:
      - "192.168.1.11"
  - name: "elastic2"
    dns:
      - "elastic2"
    ip:
      - "192.168.1.12"
  - name: "elastic3"
    dns:
      - "elastic3"
    ip:
      - "192.168.1.13"
```

come parametro dns è possibile inserire l' FQDN

Generare i certificati (anche i certificati per i nodi)

> ``/usr/share/elasticsearch/bin/elasticsearch-certutil cert --in cert-gen.yml --keep-ca-key``

If you want to use a commercial or organization-specific CA, you can use the ``elasticsearch-certutil csr`` command to generate certificate signing requests (CSR) for the nodes in your cluster.

---

## Configurare la crittografia Node-to-Node 

(To Be continued...)
