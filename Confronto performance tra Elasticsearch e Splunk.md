# Confronto performance tra Elasticsearch e Splunk

## Riferimenti
- [Guida setup Elasticsearch-Kibana-Logstash](https://github.com/anthonypope95/elasticsearch/blob/master/ElasticGuide.md)

- [Installazione Splunk su CentOS](https://www.bitsioinc.com/tutorials/install-splunk-centos/)

---

## Specifiche tecniche del sistema utilizzato

### CPU

```
[root@localhost ~]# lscpu
Architecture:          x86_64
CPU op-mode(s):        32-bit, 64-bit
Byte Order:            Little Endian
CPU(s):                2
On-line CPU(s) list:   0,1
Thread(s) per core:    1
Core(s) per socket:    1
Socket(s):             2
NUMA node(s):          1
Vendor ID:             GenuineIntel
CPU family:            6
Model:                 85
Model name:            Intel(R) Xeon(R) Silver 4214 CPU @ 2.20GHz
Stepping:              7
CPU MHz:               2194.844
BogoMIPS:              4389.68
Hypervisor vendor:     VMware
Virtualization type:   full
L1d cache:             32K
L1i cache:             32K
L2 cache:              1024K
L3 cache:              16896K
NUMA node0 CPU(s):     0,1
```

### RAM 

```
[root@localhost ~]# sudo dmidecode -t 16
# dmidecode 3.2
Getting SMBIOS data from sysfs.
SMBIOS 2.7 present.

Handle 0x0028, DMI type 16, 23 bytes
Physical Memory Array
        Location: System Board Or Motherboard
        Use: System Memory
        Error Correction Type: None
        Maximum Capacity: 9 GB
        Error Information Handle: Not Provided
        Number Of Devices: 64
```

### Disco

```
[root@localhost ~]# df
File system             1K-blocchi   Usati Disponib. Uso% Montato su
devtmpfs                   3992720       0   3992720   0% /dev
tmpfs                      4004704       0   4004704   0% /dev/shm
tmpfs                      4004704   75540   3929164   2% /run
tmpfs                      4004704       0   4004704   0% /sys/fs/cgroup
/dev/mapper/centos-root   24105472 5589840  18515632  24% /
/dev/sda1                  1038336  152512    885824  15% /boot
/dev/mapper/centos-opt    50455204 3399972  47055232   7% /opt
tmpfs                       800944       0    800944   0% /run/user/0
```

### Sistema Operativo

```
[root@localhost ~]# cat /etc/*release
CentOS Linux release 7.7.1908 (Core)
NAME="CentOS Linux"
VERSION="7 (Core)"
ID="centos"
ID_LIKE="rhel fedora"
VERSION_ID="7"
PRETTY_NAME="CentOS Linux 7 (Core)"
ANSI_COLOR="0;31"
CPE_NAME="cpe:/o:centos:centos:7"
HOME_URL="https://www.centos.org/"
BUG_REPORT_URL="https://bugs.centos.org/"

CENTOS_MANTISBT_PROJECT="CentOS-7"
CENTOS_MANTISBT_PROJECT_VERSION="7"
REDHAT_SUPPORT_PRODUCT="centos"
REDHAT_SUPPORT_PRODUCT_VERSION="7"

CentOS Linux release 7.7.1908 (Core)
CentOS Linux release 7.7.1908 (Core)
```
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

### Nota: i test delle performance sono stati eseguiti utilizzando (mantenendo attivo) un servizio per volta (Elasticsearch e Kibana / Splunk).
### È stata utilizzata una configurazione di Elasticsearch di tipo single node.


---

## Testing

### Test 1

### Query Splunk utilizzata: 
> index="main" port=8080
#### Risultato: This search has completed and has returned 242 risultati by scanning 284 events in 0,08 seconds

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
#### Risultato: This search has completed and has returned 776 risultati by scanning 1.085 events in 0,154 seconds

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

#### Risultato: This search has completed and has returned 631 risultati by scanning 866 events in 0,144 seconds

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

#### Risultato: This search has completed and has returned 546 risultati by scanning 546 events in 0,125 seconds


### Query Elasticsearch utilizzata:
> _index:"main" AND protocol:UDP AND connectorname:TenableAlfa

#### Risultato:
#### The total time the request took: 734 ms = 0.734 s
#### Query time: 246 ms = 0.246 s
#### Total hits: 546


---


*Query Time: non include il tempo impiegato per spedire la richiesta o per parsarla nel browser.*
