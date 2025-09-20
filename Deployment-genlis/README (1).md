# 📊 Log Processing & Monitoring Stack

This project contains **Log Generator**, **Log Listener**, and a **Monitoring Stack** (Prometheus, Grafana, Loki, Promtail, cAdvisor, Node Exporter).  
It is containerized with **Docker Compose** and deployed via **Jenkins Pipeline**.

---

## 🚀 Setup Instructions

### 1. Clone Repository
```bash
git clone https://github.com/vivekgshan/Log-Processing.git
cd Log-Processing
```

### 2. Build & Run Application Stack
```bash
cd Deployment-genlis
docker-compose up -d
```

- **Log Generator** → http://localhost:8780  
- **Log Listener** → http://localhost:8785  

### 3. Build & Run Monitoring Stack
```bash
cd Deployment-genlis
docker-compose -f docker-compose-monitoring.yml up -d
```

- **Prometheus** → http://localhost:9090  
- **Grafana** → http://localhost:3000 (default `admin/admin`)  
- **Loki** → http://localhost:3100  
- **Promtail** → collects logs from LogGenerator & LogListener  
- **Node Exporter** → http://localhost:9100 (host metrics)  
- **cAdvisor** → http://localhost:8081 (container metrics)  

---

## 🔧 Jenkins Pipeline

The Jenkinsfile automates:
1. Checkout source code  
2. Stop old containers & clean images  
3. Build new Docker images for LogGenerator & LogListener  
4. Deploy application stack (`docker-compose.yml`)  
5. Deploy monitoring stack (`docker-compose-monitoring.yml`)  
6. Verify running services  

Run pipeline from Jenkins with parameters:
- `LOG_COLLECTOR_IP` → Default `127.0.0.1`  
- `REMOVE_LOGGENERATOR_IMAGE` → yes/no  
- `REMOVE_LOGLISTENER_IMAGE` → yes/no  

---

## 📊 Grafana Dashboards

### Access Grafana
- URL → [http://localhost:3000](http://localhost:3000)  
- Default login → `admin / admin`

### Import Dashboards

1. Go to **Grafana → Dashboards → Import**  
2. Upload JSON file from `Deployment-genlis/grafana/dashboards/`  
3. Select Loki datasource (UID: `cey6f20ohoef4b`)  
4. Click **Import**

### Available Dashboards

- **Log Generator & Listener Dashboard**
  - **Log Tables** → logs with timestamp, log level, message  
  - **Log Volume Over Time** → spikes when heavy load occurs  
  - **Log Levels Pie Chart** → INFO / WARN / ERROR / DEBUG distribution  

- **System Metrics Dashboard**
  - **Node Exporter** → CPU, memory, disk usage  
  - **cAdvisor** → container-level metrics  

---

## 📂 Dashboard JSON Files

Dashboards can be exported and stored under:
```
Deployment-genlis/grafana/dashboards/
```

To provision automatically, mount into Grafana:
```yaml
grafana:
  volumes:
    - ./grafana/dashboards:/var/lib/grafana/dashboards
    - ./grafana/provisioning:/etc/grafana/provisioning
```

`provisioning/dashboards/dashboard.yaml`:
```yaml
apiVersion: 1
providers:
  - name: 'default'
    orgId: 1
    folder: ''
    type: file
    options:
      path: /var/lib/grafana/dashboards
```

---

## ✅ Example LogQL Queries

- **Log Table (LogGenerator)**  
```logql
{job="loggenerator"} |~ ".*"
```

- **Log Volume Over Time**  
```logql
count_over_time({job="loggenerator"}[1m])
```

- **Log Levels (Pie Chart)**  
```logql
sum by(level) (count_over_time({job="loggenerator"}[5m]))
```

(Same queries apply for `loglistener`.)

---

## 🛠 Components Overview

- **Log Generator** → produces synthetic logs  
- **Log Listener** → consumes and forwards logs  
- **Prometheus** → scrapes metrics  
- **Grafana** → visualization dashboards  
- **Loki** → log storage & querying  
- **Promtail** → log collector/shipper  
- **Node Exporter** → host system metrics  
- **cAdvisor** → container resource metrics  

---
