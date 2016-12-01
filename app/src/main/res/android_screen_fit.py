__author__ = 'jacky'

# -*- coding:utf-8 -*-

#mdpi:hdpi:xhdpi:xxhdpi:xxxhdpi=2:3:4:6:8
import os
class DimensXmlMaker:
    def __init__(self, root_path):
        self.__path = root_path
        self.base_w = 1080.0 #px
        self.base_h = 1920
        self.dir_file_name = "values-sw%ddp"
        self.file_name = "dimens.xml"

        self.soupport_screen = [
            {"w":320},
            {"w":360},
            {"w":384},
            {"w":480},
            {"w":600},
            {"w":800},
            {"w":820}
        ]

    def make_files(self):

        print("begin ----------")

        for item in self.soupport_screen:
            path = self.__path  + "/" + self.dir_file_name %(item["w"])
            if(not os.path.exists(path)):
                os.makedirs(path)
            filepath = path + "/" + self.file_name
            fp = open(filepath, "w+")
            content = self.make_strings(item)
            fp.write(content)
            fp.close()

        print("end ----------")


    def make_strings(self, dimes):
        header = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n"
        procol_header = "<resources>"
        tmp = "<dimen name=\"q%d\">%.2fpx</dimen>\n"
        ret = ""
        ret += header
        ret += procol_header
        for i in range(1, int(self.base_w) + 1):
            cell_w = dimes["w"] / self.base_w
            ret += tmp %(i, cell_w * i)

        ret += "</resources>"

        return ret

if __name__ == '__main__':
    #root_path = "source"
    root = os.getcwd() + "/"
    maker = DimensXmlMaker(root)
    maker.make_files()
    #maker =  DimensXmlMaker("")
    #maker.make_strings( {"w":360,"h": 1920})

