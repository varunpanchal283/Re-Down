c=0
def printfile(files):
	global c
	global path
	if 'folder' in files['mimeType']:
		print(files['id'])
		print(files['mimeType'])
		print(files['parents'][0]['id'])
		print(files['title'])
		print("null")
		str = "\'" + files['id'] + "\'" + " in parents and trashed=false"  
		file_list = drive.ListFile({'q': str}).GetList()
		for files in file_list:
			printfile(files)
	else:
		print(files['id'])
		print(files['mimeType'])
		print(files['parents'][0]['id'])
		print(files['title'])
		print(files['fileSize'])

import os
import pydrive
from pydrive.auth import GoogleAuth
from pydrive.drive import GoogleDrive
gauth = GoogleAuth()
# Try to load saved client credentials
gauth.LoadCredentialsFile("mycreds.txt")
if gauth.credentials is None:
    # Authenticate if they're not there
    gauth.LocalWebserverAuth()
elif gauth.access_token_expired:
    # Refresh them if expired
    gauth.Refresh()
else:
    # Initialize the saved creds
    gauth.Authorize()
# Save the current credentials to a file
gauth.SaveCredentialsFile("mycreds.txt")

drive = GoogleDrive(gauth)
cwd=os.getcwd()
idpath=cwd+"\\id.txt"
f=open(idpath,"r")
s=f.readline()
st = "\'" + s + "\'" + " in parents and trashed=false"  
file_list = drive.ListFile({'q': st}).GetList()
print(s);
print("sharedfolder");
print("null");
print("Hello world")
print("null")
for files in file_list:
	printfile(files)