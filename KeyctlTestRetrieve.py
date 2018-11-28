import subprocess

cmd = ["keyctl", "search", "@u", "user", "nuxwdog:user"]
p = subprocess.Popen(cmd, stdout=subprocess.PIPE)

keyID, errs = p.communicate()

keyID = keyID.decode().strip()

print("Python KeyID: " + keyID)

if errs:
    print("errors1: " + errs.decode())

cmd = ["keyctl", "print", keyID]

p = subprocess.Popen(cmd, stdout=subprocess.PIPE)

keyValue, errs = p.communicate()

keyValue = keyValue.decode().strip()

print("Python KeyValue: " + keyValue)

if errs:
    print("errors: " + errs.decode())