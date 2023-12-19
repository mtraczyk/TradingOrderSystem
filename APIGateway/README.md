# TradingOrderSystem

###1. How to build an image:
On Windows:
```
.\build.ps1 <image-tag>
```
or
```
.\build.ps1
```
On Unix:
```
.\build.sh <image-tag>
```
or
```
.\build.sh
```
If you won't specify tag it will be made with tag `lastest`.

##2. How to run with `docker compose`:
```
docker compose up -d --build
```
Preferably use tak `--build` to force rebuild if you don't use scripts.