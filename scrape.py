from bs4 import BeautifulSoup
import requests
import re

#https://app.testudo.umd.edu/soc/201908/CMSC
def parse(dept):
    url = r'https://app.testudo.umd.edu/soc/201908/'+dept

    url_text = requests.get(url)

    html_tree = BeautifulSoup(url_text.text, 'html.parser')

    courses = html_tree.find('div',class_='courses-container').find_all('div',id=re.compile(dept+r'[0-9]{3}[A-Z]?'),recursive=False)

    csvout = [["dept","number","suffix","title","credit","gradmeth","description","prereq_text","coreq_text","rest_text","alternate_text","gened"]]

    for c in courses:
        csv_newline = ['',0,'','',0,'','','','','','','']
        print(c['id'][:4],c['id'][4:7],c['id'][7:]) #dept, number, suffix
        print(c.find('span',class_='course-title').string) #title
        print(c.find('span',class_='course-min-credits').string) #credits
        print(c.find('span',class_='grading-method').abbr['title']) #gradmeth
        try:
            ublock = c.find('div',class_='approved-course-texts-container')
                #underblock
            first_u_div = ublock.find('div',class_='approved-course-text')
            desc_num = 0
            f_u_d_text = first_u_div.get_text()
            if ('Prerequisite' in f_u_d_text or
                'Restriction' in f_u_d_text or
                'Corequisite' in f_u_d_text or
                'Credit only granted for' in f_u_d_text):
                #First block contains additional info
                desc_num = 1
                add_info = first_u_div.contents
                #Parse out additional info
                for a in add_info:
                    print(a.text) #Prereq, Coreq, Rest, AKA
                print()

            print(ublock('div',class_='approved-course-text')[desc_num].string)
        except AttributeError: #Initial DESC pass failed
            try:
                print(c.find('div',class_='course-text').string)
            except AttributeError: #Secondary DESC pass failed
                try:
                    print(c.find('div',class_='individual-instruction-message').string)
                except AttributeError: #Tertiary DESC pass failed
                    print("Error retrieving class info due to inconsistant formatting")
            
        try:
            print(c.find('span',class_='course-subcategory').a.string) #gened
        except AttributeError:
            print('Not Gened') #No gened
        print('------\n')
        #csvout.append(csv_newline)

parse('CMSC')
