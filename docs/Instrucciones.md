# PROJECTE FINAL – 1r CURS
## Mòdul de Projecte Intermodular

---

### 1. Objectiu General
L’objectiu del projecte és desenvolupar una aplicació web completa, funcional i segura que integre:
* **Back-end:** Una base de dades operativa.
* **Front-end:** Un sistema d’interacció amb l’usuari mitjançant un servidor web.
* **Seguretat:** Mecanismes bàsics de protecció.

El projecte és **individual**. L’alumne haurà d’integrar les diferents tecnologies treballades al llarg del curs per construir un producte final coherent i documentat. No s’admetran tecnologies o conceptes que no hagen segut treballats durant el curs.

---

### 2. Requeriments del Projecte

#### 2.1. Aplicació Web
* **Backend:** Base de dades relacional (MySQL o MariaDB) amb informació real i almenys **3 taules relacionades**.
* **Frontend:** Presentació de dades mitjançant tecnologies com **Spring Boot** o **PHP**.
* **Autenticació:** Implementar mecanismes de validació d'usuaris i una **zona privada** restringida.
* **Desplegament:** Operativa en el núvol (**AWS Academy EC2**) utilitzant contenidors gestionats amb **Docker Compose**.
* **Idioma:** Part (o tota) la web haurà d'estar en **anglés**, garantint l'aplicació dels coneixements del mòdul d'anglés tècnic.

#### 2.2. Seguretat
* Funcionament mitjançant protocol **HTTPS** i certificat digital vàlid.
* Descripció del model de seguretat (estructura de xarxa i organització en zones funcionals com **DMZ**).
* Aplicació de criteris de protecció de dades i control d’accessos.

#### 2.3. Documentació Tècnica
La documentació haurà d’incloure:

**a) Descripció del Projecte**
* Nom del projecte, finalitat i objectius principals.
* Descripció funcional general i requeriments tècnics.
* Funcionalitats implementades i públic destinatari.
* Casos d’ús principals de l’aplicació.

**b) Stack Tecnològic**
* Esquema funcional del sistema amb representació gràfica.
* Separació dels components frontend i backend.
* Tecnologies emprades (JDBC, Spring Boot, PHP, Docker, MySQL, etc.).
* Relació dels ports TCP/UDP utilitzats i justificació.
* Consideracions d’arquitectura i seguretat de xarxa.

**c) Identitat Visual i Disseny**
* **Logotip:** Versió completa, clar/fosc, monocromàtica, icona i versió tipogràfica.
* **Guia d’estil:** Paleta de colors, tipografies i justificació de disseny.
* Prototipat de la interfície mitjançant **Figma**.

**d) Pla de Negoci**
* Estudi de viabilitat econòmica.
* **Anàlisi DAFO** (Debilitats, Amenaces, Fortaleses i Oportunitats).
* **Proposta CAME** derivada de l’anàlisi DAFO.

**e) Infraestructura i Desplegament**
* Registre/simulació de domini DNS.
* Desplegament en AWS EC2 i Docker.
* Gestió del codi font mitjançant un repositori **Git públic** (incloent descripció de l'estructura).

**f) Política de Còpies de Seguretat**
* Definició de política total i incremental.
* Procediment de generació de **dump SQL** i recuperació de dades.

---

### 3. Lliurables

#### 3.1. Entrega Tècnica
* Documentació completa organitzada amb format professional.
* Enllaç al repositori Git amb control de versions.
* Accés a la plataforma desplegada en AWS.

#### 3.2. Presentació i Defensa
* Presentació de la documentació en Aules i en GIT.
* **Presentació oral:** Entre 5 i 10 minuts.
* **Demostració:** Funcionament de l’aplicació en viu.
* **Defensa:** Justificació de decisions tècniques i resolució de preguntes del professorat.

---

### 4. Criteris d’avaluació
1. **Funcionament** de l’aplicació.
2. **Qualitat** del codi.
3. **Desplegament** correcte (AWS/Docker).
4. **Seguretat** (HTTPS/Accessos).
5. **Documentació** tècnica i de disseny.
6. **Presentació** i defensa oral.
