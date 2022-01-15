-Dmaven.test.skip=true
mvn -T 3 mvn -T 3C (要么指定线程数，要么指定核数)

mvn -Dmaven.multiModuleProjectDirectory=D:\workspace\java\github\poseidon-boot-starter -s E:\data\maven\settings-al.xml -Dmaven.repo.local=E:\data\maven package -Dfile.encoding=UTF-8 package