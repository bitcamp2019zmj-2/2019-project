from bs4 import BeautifulSoup
import requests
import re

def parse(dept):
    url = r'https://app.testudo.umd.edu/soc/201908/'+dept

    url_text = requests.get(url)

    html_tree = BeautifulSoup(url_text.text, 'html.parser')

    courses = html_tree.find('div',class_='courses-container').find_all('div',id=re.compile(dept+r'[0-9]{3}[A-Z]?'),recursive=False)

    csvout = [["dept","number","suffix","title","credit","gradmeth","description","prereq_text","coreq_text","rest_text","alternate_text","gened"]]

    for c in courses:
        print()
        csv_newline = ['""',0,'""','""',0,'""','""','""','""','""','""','""']

        #Header from Testudo, all in header of the course
        csv_newline[0] = c['id'][:4] #dept
        csv_newline[1] = c['id'][4:7] #number
        csv_newline[2] = c['id'][7:] #suffix
        csv_newline[3] = '"'+c.find('span',class_='course-title').string+'"' #title
        csv_newline[4] = c.find('span',class_='course-min-credits').string #credits
        csv_newline[5] = '"'+c.find('span',class_='grading-method').abbr['title']+'"' #grademeth
        try: #Gened credit?
            csv_newline[11] = '"'+c.find('span',class_='course-subcategory').a.string+'"' #gened
        except AttributeError:
            None #No gened credit

        #Body text (if exists)
        c_desc = ""
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
                    b = a.text
                    #Prereq, Coreq, Restrict, Alternate names
                    if ('Prerequisite' in b):
                        csv_newline[7] = '"'+b[14:]+'"'
                        csv_newline[7] = csv_newline[7].replace('minimum grade of ','')
                    elif ('Corequisite' in b):
                        csv_newline[8] = '"'+b[13:]+'"'
                        csv_newline[8] = csv_newline[8].replace('.','')
                    elif ('Restriction' in b):
                        csv_newline[9] = '"'+b[13:]+'"'
                    elif ('Credit only granted for' in b):
                        csv_newline[10] = '"'+b[25:]+'"'
                        csv_newline[10] = csv_newline[10].replace(' or','')
                        csv_newline[10] = csv_newline[10].replace('.','')
                        csv_newline[10] = csv_newline[10].replace(',','')

            c_desc = ublock('div',class_='approved-course-text')[desc_num].string
        except AttributeError: #Initial DESC pass failed
            try:
                c_desc = c.find('div',class_='course-text').string
            except AttributeError: #Secondary DESC pass failed
                try:
                    c_desc = c.find('div',class_='individual-instruction-message').string
                except AttributeError: #Tertiary DESC pass failed
                    print("Error retrieving class info due to inconsistant formatting")
        csv_newline[6] = '"'+c_desc+'"' #description
        
        print(csv_newline)
        csvout.append(csv_newline)

    #Output data to CSV file
    f_out = open('classes-'+dept+'.csv','w')
    for l in csvout:
        for c in l: #Includes ',' -> '§' for ease on the Java end
            print(c.replace(',','§'),end=',',file=f_out)
        print(file=f_out)
    f_out.close()

parse('CMSC')
