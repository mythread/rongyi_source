rm -rf ~/.m2/repository/com/yue
svn up yue*
for i in yue.commons* yue.biz* yue.abiz* yue.common* yue.web* yue.combiz*
do
	cd $i
	mvn -Dmaven.test.skip=true -DdownloadSources=true clean install eclipse:clean eclipse:eclipse
	cd -
done
