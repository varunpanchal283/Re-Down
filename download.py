import os
import gdown
cwd=os.getcwd()
idpath=cwd+"\\data.txt"
url="https://drive.google.com/uc?id="
f=open(idpath,"r")
c=0.0
flag=0
for x in f:
	s=list(x.split('->'))
	if not os.path.exists(s[1]):
		os.makedirs(s[1])
	gdown.download(url+s[0],s[1]+"\\"+s[2][:-1])