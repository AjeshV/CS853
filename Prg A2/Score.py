import os
import sys
import numpy

# ----------- global variables -----------------------
usageMsg = '''
Usage: Score.py [runfile] [qrelfile] 
         
'''

# -------------------------- usage() ---------------------------------------
def usage():
    if len(sys.argv) < 3 or sys.argv[1] == "-h":
        print(usageMsg)
        exit(0)

# -----------------------------------------------------------------------
# --------------------------- main --------------------------------------
# check argument usage correctness
usage()

runFile = sys.argv[1]
#"/Users/paul.yuan/Desktop/outputfile"
qrelFile = sys.argv[2]
#"/Users/paul.yuan/Desktop/test200/train.test200.cbor.article.qrels"

run_file = open(runFile)

precision = []
precisions = []
query_id = 'to_make_life_easier'
query_count_tp = 0.0
query_count_rel = 0.0
is_first_line = 0
list_precision = []
list_para = []
i_rel = 1

pre_map = []
big_map = []

list_dcg = []
list_idcg = []
list_ndcg = []
list_dcg_all = []
list_idcg_all = []

for line in run_file.readlines():

    new_line = line.split()

    if not line.startswith(query_id):

        list_para = []

        if is_first_line != 0:
            list_precision.append(query_count_tp / query_count_rel)
            big_map.append(sum(pre_map)/len(pre_map))
            pre_map = []
            list_dcg_all.append(sum(list_dcg)/20.0)
            list_dcg = []
            list_idcg_all.append(sum(list_idcg)/20.0)
            list_idcg = []
        if is_first_line == 0:
            is_first_line = 1

        query_count_tp = 0.0
        query_id = new_line[0]

        rel_file = open(qrelFile)

        for line in rel_file.readlines():

            if line.startswith(query_id):
                rel_line = line.split()
                list_para.append(rel_line[2])

        rel_file.close()

        query_count_rel = (float)(len(list_para))
        para_id = new_line[2]

        i_rel = 1

        if para_id in list_para and i_rel < (query_count_rel+1):
            query_count_tp += 1

        if i_rel < (query_count_rel + 1):
            pre_map.append(query_count_tp/i_rel)

        if i_rel < 21:
            if para_id in list_para:
                list_dcg.append(1.0/(numpy.log2(1+i_rel)))
                len_idcg = len(list_idcg)
                list_idcg.append(1.0 / numpy.log2(1 + len_idcg + 1))
            else:
                list_dcg.append(0.0)


        i_rel += 1

    else:

        para_id = new_line[2]

        if para_id in list_para and i_rel < (query_count_rel+1):
            query_count_tp += 1

        if i_rel < (query_count_rel + 1):
            pre_map.append(query_count_tp/i_rel)

        if i_rel < 21:
            if para_id in list_para:
                list_dcg.append(1.0/(numpy.log2(1+i_rel)))
                len_idcg = len(list_idcg)
                list_idcg.append(1.0 / numpy.log2(1 + len_idcg + 1))
            else:
                list_dcg.append(0.0)

        i_rel += 1

print ("Precision@R is " + str(sum(list_precision)/len(list_precision)))
#print (big_map)
print ("MAP is " + str(sum(big_map)/len(big_map)))
print ("nDCG is " + str(sum(list_dcg_all)/sum(list_idcg_all)))
#print (list_dcg_all)
#print (list_idcg_all)