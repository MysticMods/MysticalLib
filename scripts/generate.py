#!/usr/bin/env python3

import sys
import json
import os

TYPES = ["block", "button", "door", "double_slab", "fence", "fence_gate", "log", "pressure_plate", "slab", "stairs", "trapdoor", "wall"]

def main (args):
    if len(args) == 0:
        return

    dir = os.getcwd()

    if not dir.endswith("MysticalLib"):
        dir = dir[0:-len(dir.split("\\")[-1])]

    template_path = os.path.join(dir, "MysticalLib", "scripts", "templates")

    input_data = args[1]
    with open(input_data) as o:
        data = json.load(o)

    directory = data["directory"]
    texture_base = data["texture_base"]

    for model in data["models"]:
        base = model["base"]
        texture = model.get("texture", base)
        type = model["type"].lower()

        if type not in TYPES:
            print("Invalid type: " + type)
            continue

        this_template = os.path.join(template_path, type + ".json")
        if not os.path.exists(this_template):
            print("Invalid template or template does not exist: " + this_template)

        with open(this_template) as o:
            template_data = o.read()

        this_texture = texture_base + texture

        texture_params = tuple([this_texture, ] * template_data.count("%s"))

        if type == "block":
            filename = os.path.join(directory, base + ".json")
        else:
            filename = os.path.join(directory, base + "_" + type + ".json")

        print("Attempting to create " + filename)

        with open(filename, "w") as o:
            o.write(template_data % texture_params)


if __name__=="__main__":
    main(sys.argv)