import { connect } from "mongoose";
import { NextApiRequest, NextApiResponse } from "next";
import { signup } from "../../../controllers/user";
import { msgError, user } from "../../../utils/types";

export default async function handler(req: NextApiRequest, res: NextApiResponse<user | msgError>){
    if(req.method === "POST"){
        await connect(process.env.MONGODB_URL as string);
        signup(req, res);
    }else
        res.status(404).json({
            message: `${req.method} not supported for this endpoint`,
        })
}