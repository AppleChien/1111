from collections import OrderedDict
from NonTerminal import NonTerminal
from Terminal import Terminal
from re import *

t_list = OrderedDict()
nt_list = OrderedDict()
production_list = []


def grammar_fromstr(g):#那個|怎麼弄
    '''1 split each line
    2 separate LHS from RHS
    3 split HRSs i.e.: X => Y | Z
    4 for each RHS r, make a rule LHS => r
    '''
    grm = g.split('\n')
    rules = []
    for r in grm:
        if r.strip()=='':continue # skip empty lines
        lhs, rhs_ls = r.split('->')
        for rhs in rhs_ls.split('|'):
            rules.append((lhs.strip(),rhs.strip().split()))
    return rules


def main():
	global production_list, t_list, nt_list

	i=0
	count=len(open('../input/2/2_grammar.txt','r').readlines())
	f = open('../input/2/2_grammar.txt','r')
	linelist=f.readlines()
	for line in range(len(linelist)):
		linelist[line] = linelist[line].strip()
	
	
	for line in linelist:
		i+=1
		"""
		if(i==1):
			t_list=line[0][10:]
			t_list=t_list.split(', ')
			t_list.append('$')
		if(i==2):
			nt_list=line[0][13:]
			nt_list=nt_list.split(', ')
			nt_list.append('$')"""
		if(i>=3 and i<=count):
			#grammar_fromstr(line)
			production_list.append(line)
			print(line)
	
	print(production_list)	
	
	
	ctr=1
	t_regex, nt_regex = r'[a-z\W]',  r'[A-Z]'
	
	while True:
		#production_list.append(input().replace(' ', ''))
		#if production_list[-1].lower() in ['end', '']: 
			#del production_list[-1]
			#print(production_list)
			#break
		if(ctr==count-1):
			break

		head, body = production_list[ctr-1].split('->')

		if head not in nt_list.keys():
			nt_list[head] = NonTerminal(head)

		#for all terminals in the body of the production
		for i in finditer(t_regex, body):	#returns an iterator object in the ordered matched.
			s = i.group()			#since the group argument is empty, it'll return whatever it matched completely
			if s not in t_list.keys():
				t_list[s] = Terminal(s)

		#for all non-terminals in the body of the production
		for i in finditer(nt_regex, body):
			s = i.group()
			if s not in nt_list.keys():
				nt_list[s] = NonTerminal(s)
				
		ctr+=1


