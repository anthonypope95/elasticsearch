# Elasticsearch guide


## Riferimenti
- [ElasticSearch cluster in production (3 nodes)](https://medium.com/@diogo.fg.pinheiro/elasticsearch-cluster-in-production-3-nodes-668221bbd952)
- [Install Elasticsearch with Debian Package](https://www.elastic.co/guide/en/elasticsearch/reference/7.6/deb.html#deb)


---

## Prerequisiti

Installare Java

### In debian
> ``sudo apt install default-jre``

### In CentOS
> ``yum install java-11-openjdk-devel``



---

## Installazione Elasticsearch



### In debian

> ``wget https://artifacts.elastic.co/downloads/elasticsearch/elasticsearch-7.6.1-amd64.deb``
>
> ``wget https://artifacts.elastic.co/downloads/elasticsearch/elasticsearch-7.6.1-amd64.deb.sha512``
>
> ``shasum -a 512 -c elasticsearch-7.6.1-amd64.deb.sha512``
>
> ``sudo dpkg -i elasticsearch-7.6.1-amd64.deb``


### In CentOS

> ``wget https://artifacts.elastic.co/downloads/elasticsearch/elasticsearch-7.6.1-x86_64.rpm``
>
> ``wget https://artifacts.elastic.co/downloads/elasticsearch/elasticsearch-7.6.1-x86_64.rpm.sha512``
>
> ``shasum -a 512 -c elasticsearch-7.6.1-x86_64.rpm.sha512``
>
> ``sudo rpm --install elasticsearch-7.6.1-x86_64.rpm``

---

## Configurazione Elasticsearch

Accedere al file di configurazione di Elasticsearch

> ``sudo vi /etc/elasticsearch/elasticsearch.yml``

### Configurazione del nodo Master

``` 
cluster.initial_master_nodes: ["ip_nodo_master"]

# give your clust a name (same for all nodes)

cluster.name: es-cluster

# give your node a name (the same as your hostname)

node.name: "es-master"

# define node 1 as master-eligible (only for master node)

node.master: true

# define node 2 and 3 as data nodes (only for data nodes)

node.data: true

# enter the private IP and port of your node (the same ip as your machine)

network.host: ip_nodo
http.port: 9200

# detail the private IPs of your nodes:

discovery.seed_hosts: ["ip_nodo1", "ip_nodo2", ....]

# to avoid split brain ([Master Eligible Node) / 2 + 1])

discovery.zen.minimum_master_nodes: 1 
```



### Configurazione degli altri nodi

```
cluster.initial_master_nodes: ["ip_nodo_master"]
cluster.name: es-cluster

# give your node a name (the same as your hostname)

node.name: "es-peer2"

# define node 1 as master-eligible (only for master node)

node.master: false

# define node 2 and 3 as data nodes (only for data nodes)

node.data: true

# enter the private IP and port of your node (the same ip as your machine)

network.host: 10.10.10.101
http.port: 9200

# detail the private IPs of your nodes:

discovery.seed_hosts: ["ip_nodo1", "ip_nodo2", ....]
```

---

## Installazione Kibana

Kibana va installato su un nodo in cui è presente anche il servizio Elasticsearch

### In Debian

>``wget https://artifacts.elastic.co/downloads/kibana/kibana-7.6.1-amd64.deb``
>
>``shasum -a 512 kibana-7.6.1-amd64.deb`` 
>
>``sudo dpkg -i kibana-7.6.1-amd64.deb``


### In CentOS
#### Installing from the RPM repository

Create a file called kibana.repo in the /etc/yum.repos.d/ directory 

#### Contenuto del file

```
[kibana-7.x]
name=Kibana repository for 7.x packages
baseurl=https://artifacts.elastic.co/packages/7.x/yum
gpgcheck=1
gpgkey=https://artifacts.elastic.co/GPG-KEY-elasticsearch
enabled=1
autorefresh=1
type=rpm-md
```

Eseguire il seguente comando

> ``sudo yum install kibana``

---

## Configurazione Kibana


```
# Kibana is served by a back end server. This setting specifies the port to use.

server.port: 5601

# Specifies the address to which the Kibana server will bind. IP addresses and host names are both valid values.

# The default is 'localhost', which usually means remote machines will not be able to connect.

# To allow connections from remote users, set this parameter to a non-loopback address.

server.host: "ip_host_kibana_elastic"
elasticsearch.hosts: ["http://ip_host_kibana_elastic:9200"]
```


---

## Installazione Logstash

### In Debian

- [Install Logstash in Debian](https://www.elastic.co/guide/en/logstash/7.6/installing-logstash.html#_apt)


> ``wget -qO - https://artifacts.elastic.co/GPG-KEY-elasticsearch | sudo apt-key add -``
>
>``sudo apt-get install apt-transport-https``
>
>``echo "deb https://artifacts.elastic.co/packages/7.x/apt stable main" | sudo tee -a /etc/apt/sources.list.d/elastic-7.x.list``
>
> ``sudo apt-get update && sudo apt-get install logstash``



### In CentOS

Add the following in your /etc/yum.repos.d/ directory in a file with a .repo suffix, for example logstash.repo

```
[logstash-7.x]
name=Elastic repository for 7.x packages
baseurl=https://artifacts.elastic.co/packages/7.x/yum
gpgcheck=1
gpgkey=https://artifacts.elastic.co/GPG-KEY-elasticsearch
enabled=1
autorefresh=1
type=rpm-md
```

Eseguire il seguente comando

>``sudo yum install logstash``


---

## Configurazione Logstash

Creare file di configurazione per effettuare il parsing

Esempio di contenuto del file logstash.conf:

```
input{ 

       file{                      
            
            path => "/home/rambo/username.csv"
            start_position => "beginning"
            sincedb_path => "/dev/null"                      
            
            }             
} 

filter{

        csv{
            
            separator => ","
            columns => ["Username","Identifier","First name","Last name"]                                             
            
            }             
}

output{

         elasticsearch{    
            
            hosts => ["http://ip_nodo1:9200","http://ip_nodo2:9200","http://ip_nodo3:9200","http://ip_nodo4:9200"]               
            index => "username"      
            
            }

        stdout{
        
           }

}
```

Nel seguente esempio è stato parsato un file .csv

---

## Lanciare Logstash

Recarsi nella seguente directory: 

> ``cd /usr/share/logstash``

Eseguire il seguente comando per lanciare Logstash

> ``bin/logstash -f /home/rambo/logstash.conf``
