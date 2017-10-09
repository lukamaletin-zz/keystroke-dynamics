import pandas as pd
from sklearn.model_selection import train_test_split
from sklearn.preprocessing import StandardScaler
from sklearn.neural_network import MLPClassifier
from sklearn import svm
from sklearn.metrics import confusion_matrix, classification_report


data = pd.read_csv('data.csv')
#print(data.head().transpose())
#print(data.describe().transpose())
#print(data.shape)
x = data.drop('person', 1)
y = data['person']
x_train, x_test, y_train, y_test = train_test_split(x, y)

scaler = StandardScaler()
scaler.fit(x_train)
x_train = scaler.transform(x_train)
x_test = scaler.transform(x_test)

clf = MLPClassifier(hidden_layer_sizes=(19, 19, 19), max_iter=500)
clf.fit(x_train, y_train)
predictions = clf.predict(x_test)
print('---MLP---')
print('confusion matrix:')
print(confusion_matrix(y_test, predictions))
print('classification report:')
print(classification_report(y_test, predictions))

clf = svm.SVC()
clf.fit(x_train, y_train)
predictions = clf.predict(x_test)
print('---SVM---')
print('confusion matrix:')
print(confusion_matrix(y_test, predictions))
print('classification report:')
print(classification_report(y_test, predictions))