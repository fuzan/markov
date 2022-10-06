# Text Generator 
## 

A service will generate some randome text based on the input via Markov chain.

- SpringBoot as web framework, SpringBoot version 2.6.12
- Java version 11.0.15
- mvn version 3.2.5

## How to run

Project is dockerized. docker deamon is needed. And in order to build, you
need docker-compose.

####Docker and Docker-compose version:
- Version:           19.03.5
- API version:       1.40
- Docker-compose:    1.24.1

### Steps

- build docker images, please in the top level folder.

```sh
docker-compose up
```

if everything working fine, you are suppose to see the output:
![img_5.png](img_5.png)

- Open Brower to verify the result.
```sh
http://localhost:9001
```

upload any text file( size must be smaller than 512KB )

![img_4.png](img_4.png)

click generate button and see the text

![img_1.png](img_1.png)

change the key size and generate word size to see any difference. Key size maximum is 6, and word
number is at most 100.

![img_2.png](img_2.png)

## Project Structure

Core logic is MarkovChainService, which is build a Markoy node chain,
each( key size ) word/words will be a prefix, and its postfix is only depending on its predecessor.

![img_3.png](img_3.png)
