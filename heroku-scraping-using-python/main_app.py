import flask
from bs4 import BeautifulSoup
import json
import requests
app = flask.Flask(__name__)

@app.route("/")
def index():
	data=[]
	content=requests.get("https://market.todaypricerates.com/Warangal-vegetables-price-in-Telangana").text
	soup=BeautifulSoup(content,'lxml')
	#soup=BeautifulSoup(content,features="html.parser")
	for a in soup.find_all(class_='Row'):
		row_items = a.find_all(class_='Cell')
		json_entry = {'vegname': row_items[0].text,'market': (row_items[2].text)[3:],'retail': (row_items[3].text)[3:],'shopping': (row_items[4].text)[3:],}
		data.append(json_entry)
	return json.dumps(data)