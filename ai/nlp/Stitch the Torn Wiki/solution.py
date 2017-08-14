from typing import List
from nltk.probability import FreqDist
import math
from _collections import defaultdict
import sys
import numpy
from sympy.matrices.densetools import row

debug = False

class Document:

    def __init__(self, d):
        self.d = d
        self.fd = FreqDist(word.lower() for word in d.split(" ") if word.isalpha() and word.lower() not in stop_words )
        self.tf = dict((i, 1+math.log(self.fd[i])) for i in self.fd.keys())
        self.idf = {}
        self.wt = {}
        
    def dotProduct(self, d):
        keys = set(self.wt.keys()).intersection(d.wt.keys())
        printlog(self.d[:10], d.d[:10], keys)
        return sum([self.wt[p]*d.wt[p] for p in keys])

def printlog(*data):
    if debug: 
        print(data)   
             
def computeIDF(documents: List[Document]):
    
    for d in documents:
        idf = {}
        wt = defaultdict(lambda: 0)        

        for w in d.fd.keys():
            idf[w] =  math.log(N / len([1 for doc in documents if w.lower() in doc.fd]))
            wt[w] = d.tf[w]*idf[w]

        L = math.sqrt(sum(x*x for x in wt.values()))

        for w in wt.keys():
            wt[w] = wt[w] / L
            if w.title() in d.d: 
                wt[w] = wt[w]*2               
            
        d.idf = idf
        d.wt = wt
        
     
# stop_words = set(stopwords.words('english'))
stop_words = {'itself', 'all', 's', 'had', 'and', 'down', 'off', 'each', 'because', 'aren', 'by', 'she', 'nor', 'most', 'shouldn', 'is', 'them', 'needn', 'or', 'are', 'over', 'been', 'he', 'this', 'to', 'why', 'then', 'about', 'once', 'what', 'you', 'doing', 'has', 'with', 'did', 'so', 'whom', 'into', 'these', 'the', 'ma', 'it', 'isn', 'further', 'me', 'wouldn', 'yourself', 'same', 'above', 'don', 'not', 'shan', 'again', 'those', 'where', 'if', 'in', 'below', 'only', 'just', 'now', 'that', 'i', 'they', 'there', 'more', 'o', 'for', 'having', 't', 'have', 'myself', 'at', 'as', 'wasn', 'an', 'too', 'some', 'd', 'be', 'through', 'll', 'out', 'on', 'both', 'here', 'yourselves', 'its', 'couldn', 'my', 've', 'than', 'no', 'between', 'while', 'their', 'against', 'how', 'herself', 'won', 'am', 'doesn', 'other', 'after', 'hadn', 'hasn', 'her', 'from', 'weren', 'which', 'ours', 'when', 'your', 'hers', 'few', 'mightn', 'can', 'does', 'themselves', 'haven', 'who', 'mustn', 'under', 'own', 'being', 'm', 'during', 'our', 'him', 'a', 'but', 'will', 're', 'we', 'of', 'should', 'before', 'ourselves', 'theirs', 'until', 'such', 'was', 'were', 'do', 'his', 'ain', 'any', 'up', 'himself', 'y', 'yours', 'very', 'didn'}
stop_words.update(['.', ',', '"', "'", '?', '!', ':', ';', '(', ')', '[', ']', '{', '}'])

# with open("Test/TestCase1.test") as f:
#     data = f.readlines()
data = sys.stdin.readlines()               
N = int(data[0])
setA = list(Document(d) for d in data[1:N+1])
separator = data[N+1]

if separator.strip() != "*****":
    print("Exiting")
    exit()
setB = list(Document(d) for d in data[N+2:])

computeIDF(setA)
computeIDF(setB)

for d in setA:
    lt=[ (v,k) for k,v in d.wt.items() ]
    printlog(d.d[:10], lt)
 
for d in setB:
    lt=[ (v,k) for k,v in d.wt.items() ]
    printlog(d.d[:10], lt)
    
    
similarity = numpy.array([[d1.dotProduct(d2) for d2 in setB] for d1 in setA])
result=[0]*N
cols=[]

for i,r in enumerate(similarity):
    printlog(i, list(r))

result = [-1]*N
for _ in range(N):
    maxSimilarity, maxrow, maxcol = -1, -1, -1
    for row in range(N):
        if result[row] > -1:
            continue
        for col in range(N):
            if col in result:
                continue
            if maxSimilarity < similarity[row][col]:
                maxSimilarity = similarity[row][col]
                maxrow = row
                maxcol = col
    
    result[maxrow] = maxcol
        
for x in result:
    print(x+1)
