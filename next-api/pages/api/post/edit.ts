import { unlink } from "fs";
import { NextApiHandler, NextApiRequest, NextApiResponse } from "next";

export default async function handler(req: NextApiRequest, res: NextApiResponse){
    console.log(req.body);
    unlink("./public/images/", (err)=>{
        if(err)
            console.log(err)
        res.status(200).json({
            message: "deleted!",
        })
    });
}