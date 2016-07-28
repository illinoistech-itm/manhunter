Project Manhunter

DESCRIPTION

Project Manhunter is a project developed by BSMP (Brazil Scientific Mobility Program) students attending to the Summer Research Program at Illinois Institute of Technology. Oriented by Professor Jeremy Hajek, the students developed the infrastructure and performed an attack on it based on the thesis, Leakage-Abuse Attacks Against Searchable Encryption, created by Dr. Jason Perry and his team.

AUTHORS

Gabriel Escudeiro, Universidade Federal do Pará, Brazil. (gabrielescudeiro1@gmail.com)
Jhonatan Alves, Universidade Federal de Brasília, Brazil.(joliveiraalves@hawk.iit.edu)
Paulo Sarmento, Universidade Federal do Pará, Brazil.(plobatosarmento@hawk.iit.edu)
Ygor Santos, Pontifícia Universidade Católica de Minas Gerais, Brazil. (ygorhsantos@gmail.com)

PREREQUISITES

NetBeans 6.0+ or equivalent installed.
In case using outside Illinois Institute of Technology, change the URL on the S3 Sample class.
Be able to run Python scripts, through command line or separate application.

TO RUN THE ATTACK

Run the Python script (extractKeywords.py) to analyze and create metadata file.
Run the Python script (encrypt.py) to encrypt the files.
Run Java code. Select option 1 to create buckets and send the files to the Object Storage.
Run Java code and select option 5 to attack the encrypted data. 
Wait for the code to process. A message will be shown asking for the Python script to be run again on the words files.
Run the Python script to encrypt the words in the WordsToEncrypt folder, under src.
 
BUILT WITH

NetBeans 8.0.1
Java SDK S3 Sample 1.9.4
Python 2.7
HP Eucalyptus 4.0.2
NLTK 3.0
Vagrant 1.8.5
Linux Ubuntu 16.04

ACKNOLEDGEMENTS

Dr. Jason Perry.
Professor Jeremy Hajek.
Illinois Institute of Technology.
CAPES (Coordenação de Aperfeiçoamento de Pessoal de Nível Superior).
IIE (Institute of International Education).
