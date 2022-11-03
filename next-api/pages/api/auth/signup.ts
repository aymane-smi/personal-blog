import { connect } from "mongoose";
import { NextApiRequest, NextApiResponse } from "next";
import { signup } from "../../../controllers/user";
import { msgError, user } from "../../../utils/types";

export default async function handler(req: NextApiRequest, res: NextApiResponse<user | msgError>){
    if(req.method === "POST"){
        console.log(process.env.MONGODB_URL);
        await connect(process.env.MONGODB_URL);
        signup(req, res);
    }else
        res.status(404).json({
            message: `${req.method} not supported for this endpoint`,
        })
}