#!/usr/bin/env python3

import sys
import json
import os

TYPES = ["item", "block", "button", "door", "double_slab", "fence", "fence_gate", "log", "pressure_plate", "slab", "stairs", "trapdoor", "wall", "tool", "runed_wood"]

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
    blocks = data["thaumcraft.blocks"]
    items = data["thaumcraft.items"]

    for model in data["models"]:
        base = model["base"]
        texture = model.get("texture", base)
        type = model["type"].lower()
        this_texture = texture_base + texture


        if type not in TYPES:
            print("Invalid type: " + type)
            continue

        if type == "wall":
            wall_template = open(os.path.join(template_path, "wall.json")).read()
            wall_inventory = open(os.path.join(template_path, "wall_inventory.json")).read()
            wall_inventory_item = open(os.path.join(template_path, "wall_inventory_item.json")).read()
            wall_side = open(os.path.join(template_path, "wall_side.json")).read()
            wall_post = open(os.path.join(template_path, "wall_post.json")).read()
            filename = os.path.join(directory, base + "_wall.json")

            this_texture2 = texture_base + texture
            with open(os.path.join(blocks, base + "_wall_inventory.json"), "w") as o:
                o.write(wall_inventory % tuple([this_texture2, ] * wall_inventory.count("%s")))

            with open(os.path.join(blocks, base + "_wall_side.json"), "w") as o:
                o.write(wall_side % tuple([this_texture2, ] * wall_side.count("%s")))


            with open(os.path.join(blocks, base + "_wall_post.json"), "w") as o:
                o.write(wall_post % tuple([this_texture2, ] * wall_post.count("%s")))

            this_texture2 = texture_base.replace("thaumcraft.blocks/", "") + base
            with open(filename, "w") as o:
                o.write(wall_template % tuple([this_texture2, ] * wall_template.count("%s")))

            this_texture2 = texture_base.replace("thaumcraft.blocks", "block") + base
            with open(os.path.join(items, base + "_wall.json"), "w") as o:
                o.write(wall_inventory_item % tuple([this_texture2, ] * wall_inventory_item.count("%s")))
        else:
            this_template = os.path.join(template_path, type + ".json")
            if not os.path.exists(this_template):
                print("Invalid template or template does not exist: " + this_template)

            with open(this_template) as o:
                template_data = o.read()

            if type == "log" or type == "runed_wood":
                texture_params = tuple([model["log"], this_texture])
            else:
                texture_params = tuple([this_texture, ] * template_data.count("%s"))

            if type == "block" or type == "item" or type == "tool" or type == "log" or type == "runed_wood":
                filename = os.path.join(directory, base + ".json")
            else:
                filename = os.path.join(directory, base + "_" + type + ".json")

            print("Attempting to create " + filename)

            with open(filename, "w") as o:
                o.write(template_data % texture_params)


if __name__=="__main__":
    main(sys.argv)