import requests
import re
import os

cwd=os.getcwd()
idpath=cwd+"\\id.txt"
f=open(idpath,"r")
s=f.readline()
st="https://drive.google.com/uc?id="+s
res=requests.get(st)
x=res.headers
len_file='Content-Length'
if len_file in x:
    m = re.search('filename="(.*)"', res.headers["Content-Disposition"])
    filename_from_url = m.groups()[0]
    print(filename_from_url)
    print(x[len_file])
    print(x['Content-Type'])
    print(s)
else:
    st="https://drive.google.com/u/0/uc?id="+s+"&export=download"
    r=requests.get(st)
    find=r.text
    temp=s+'">(.*)</span> is t'
    full=re.search(temp,find)
    ress=full.group()
    filename_obj=re.search('>(.*)</a>',ress)
    filename=filename_obj.group()[1:-4]
    print(filename)
    size_obj=re.search('/a>(.*)\\)',ress)
    print(size_obj.group()[5:-1])
    print(os.path.splitext(filename)[-1][1:])
    print(s)