# -*- coding: utf-8 -*-
"""
@author: jains
"""


from flask import Flask
from flask_restful import Resource, Api
from flask import request, jsonify
import spacy
from spacy.lang.en.stop_words import STOP_WORDS
from string import punctuation
import warnings
warnings.filterwarnings('ignore')

app = Flask(__name__)
api = Api(app)


def message_summarizer(text,clubs):
    stopwords=list(STOP_WORDS)
    spacy.cli.download('en')
    nlp=spacy.load('en_core_web_sm')
    
    doc=nlp(text)
    
    tokens=[token.text for token in doc]
    
    for i in clubs:
        if i in tokens:
            
            word_frequencies={}
            for word in doc:
                if word.text.lower() not in stopwords:
                    if word.text.lower() not in punctuation:
                        if word.text not in word_frequencies.keys():
                            word_frequencies[word.text]=1
                        else:
                            word_frequencies[word.text]+=1
            
            max_frequency=max(word_frequencies.values())
            
            for word in word_frequencies.keys():
                word_frequencies[word]=word_frequencies[word]/max_frequency
            
            sentence_tokens=[sent for sent in doc.sents]
            
            sentence_scores={}
            for sent in sentence_tokens:
                for word in sent:
                    if word.text.lower() in word_frequencies.keys():
                        if sent not in sentence_scores.keys():
                            sentence_scores[sent]=word_frequencies[word.text.lower()]
                        else:
                            sentence_scores[sent]+=word_frequencies[word.text.lower()]
                            
            from heapq import nlargest
            
            select_length=int(len(sentence_tokens)*0.4)
            summary=nlargest(select_length,sentence_scores,key=sentence_scores.get)
            final_summary=[word.text for word in summary]
            summary=''.join(final_summary)
            
            return summary
            
        else:
            return
        

class HelloWorld(Resource):
    def get(self ):
        raw_text=request.get_json()
        texts=raw_text.split("\n")
        club=texts[0]
        clubs=club['preferences']
        raw_texts=texts[1]
        text=raw_texts['message']
        t=message_summarizer(text,clubs)
        return t

'''
raw data is in the form
{preferences:[clubs]}
{message:"message"}
 
'''


api.add_resource(HelloWorld, '/')

if __name__ == '__main__':
    app.run(debug=True)