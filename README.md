# Data-Based Network Security System
## Introduction to the DBNS
DBNS is a network security system composed of latest opensource big data components. Within the architecture building and efficiency optimization, the DBNS system can afford various methods to analysis 1GBPS network flow within a single node server, and up to 10GBPS network flow within a three-node server cluster. In the github, we show the source code of the single-node mode and wish you to provide your advice for our project. 

DBNS system is developed by **Network Computing Center** of **Shanghai Jiaotong University**. You can contact luoshengjie@sjtu.edu.cn for more information.

## DBNS Architecture
DBNS system is made of four layers.
- Information Layer: Collect the package from the NIC and resolver the package by PCAP components
  ![layer1](https://github.com/ShengjieLuo/DBNS/blob/master/doc/images/layer1.jpg)
- Platform Layer: A online stream computing platform to distribute the package data into different database. Some online statitic process is finished here to cutoff the overload in following steps.
  ![layer2](https://github.com/ShengjieLuo/DBNS/blob/master/doc/images/layer2.jpg)
- Analysis Layer: Online analysis, which provides report each minute, and offline analysis, which provides report each hour are main part of this layer. DBNS takes a duty of network monitor and problem detection.
  ![layer3](https://github.com/ShengjieLuo/DBNS/blob/master/doc/images/layer3.jpg)
- Displayer Layer: Well-designed web UI for users to employ the analysis result.
  ![layer4](https://github.com/ShengjieLuo/DBNS/blob/master/doc/images/layer4.jpg)

## DBNS Schedule
- version 0.1: released in 01.15.
  
  Content: A single-node demo within the install script. The network monitor function is focused in this stage and problenm detection would be simple temporarily.
  ![version01](https://github.com/ShengjieLuo/DBNS/blob/master/doc/images/version01.jpg)
  
- version 0.2: released in 02.30
  
  Content: A multi-node version would be released. 
  ![version02](https://github.com/ShengjieLuo/DBNS/blob/master/doc/images/version02.jpg)
  
- version 1.0: released in 03.30
  
  Content: The official version. The network would include all function in these documents.
  ![version10](https://github.com/ShengjieLuo/DBNS/blob/master/doc/images/version10.jpg)
- version 2.0: released in 04.30
  
  Content: The Graphic-Computing version. Graphic Computing components would be part of the DBNS system.
  ![version20](https://github.com/ShengjieLuo/DBNS/blob/master/doc/images/version20.jpg)

## Wish you to be member of our team!
