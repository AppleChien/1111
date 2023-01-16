input_file=open("symbols.txt",'r')
stand=input("Input:")
rule={}
count=(len(stand)//4)+1

n = 0
for i in range(count):
    rule[stand[n]] = stand[n+2]
    n += 4
    
for line in input_file:
    for location in line:
        if location in rule:
            print(rule[location],end="")
        else:
            print(location,end="")
        
#*:1,#:2,@:3,$:4