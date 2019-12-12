# BillPrinterAPI
Java application to print bills based on the given order JSON  data

Opens a serversocket to recieve json data and print it using a connected printer.

JSON file must follow the schema strictly.

Technologies Used:
1. Java Standard Edition 1.8 (Java 8)
2. Quartz Scheduler
3. Google API Client, API Services Drive, OAuth Client
4. Log4j framework for file and console logging
5. Socket API

System was tested and ran successfully on the following devices:
1. Windows 10 with JDK8
2. Ubuntu 18.04 with JDK10
3. Raspian with JRE11

The printing tests were conducted on the following devices:
1. Canon LBP611Cn
2. TSC TE200 (80mm)
3. HPRT MPT-II (58mm)

The latter version of the project was modified to fit the 58mm paper of MPT-II.

Zj-58 Drivers were used on the HRPT printer while working with the raspberry system.

Future Agendas:
1. AES CBC Decryption
2. Improvements in Bill formatting and spacing
3. Logging to remote NoSQL Database

