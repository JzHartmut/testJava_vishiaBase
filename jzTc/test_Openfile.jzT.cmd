

==JZtxtcmd==

currdir = <:><&scriptdir>/../../..<.>; ##root of the component

sub test_Openfile() {
  mkdir build/test_JZtxtcmd;               
  String fname = "build/test_JZtxtcmd/test_output_Openfile.txt";
  ##build the absolute path, elsewhere uses system's currdir 
  ##because it is an immediately Java operation:
  FileSystem.delete(<:><&currdir>/<&fname><.>);
  ##In opposite all JZtxtcmd operations use the JZtxtcmd internal currdir
  Openfile ftest += fname;     ##Test whether += for append works on non existent file             
  <+ftest>Test content<.+>
  ftest.close();
  
  String fname2 = "build/test_JZtxtcmd/test_output_Openfile2.txt";
  String sRead1 = FileSystem.readFile(File: &fname);
  Openfile ftest2 = fname2;
  <+ftest2>sum content #><&sRead1><#<:n><.+>
  ftest2.close();
  
  
  String XXXfname3 = "build/test_JZtxtcmd/test_output_Openfile3.txt";
  Openfile ftest3 += fname2;
  <+ftest3>sum content #><.+>
  FileSystem.readFile(File: &fname, ftest3, null);
  <+ftest3><#<:n><.+>
  ftest3.close();
  
  



}

main() {

  call test_Openfile();

}