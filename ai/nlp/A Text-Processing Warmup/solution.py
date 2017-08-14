import sys
from nltk.probability import FreqDist
from nltk.tokenize.regexp import regexp_tokenize
import re


lines = sys.stdin.readlines()

N = int(lines[0])
data = lines[1:]

for d in data:
    if d.strip() == "":
        continue
    sent = FreqDist(word.lower() for word in regexp_tokenize(d, pattern=r'[a-zA-Z0-9]+'))
    
    print(sent["a"])
    print(sent["an"])
    print(sent["the"])
    
    seperator = r'[ \\/,.-]+'
    datePattern = r'\d{1,2}(?:st|nd|rd|th)*'

    monthPattern = r'(?:\d\d|jan|feb|mar|apr|may|jun|july|aug|sep|oct|nov|dec|january|february|march|april|may|june|july|august|september|october|november|december)+'

    yearPattern = r'\d{2,4}'
    pattern = datePattern + seperator + monthPattern + seperator + yearPattern
#     print(re.findall(pattern, d, re.IGNORECASE));
    dates = len(re.findall(pattern, d, re.IGNORECASE));

    pattern = monthPattern + seperator + datePattern + seperator + yearPattern
#     print(re.findall(pattern, d, re.IGNORECASE));
    dates = dates + len(re.findall(pattern, d, re.IGNORECASE));
    print(dates)