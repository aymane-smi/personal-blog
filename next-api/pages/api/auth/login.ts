import { connect } from "mongoose";
import { NextApiRequest, NextApiResponse } from "next";
import { userAgent } from "next/server";
import { login } from "../../../controllers/user";
import { loginResponse, msgError } from "../../../utils/types";

export default async function handler(req: NextApiRequest, res: NextApiResponse<loginResponse | msgError>){
    await connect(process.env.MONGODB_URL as string);
    if(req.method === "POST")
        login(req, res);
    else
        res.status(404).json({
            message:`${req.method} not supported for this endpoint`,
        })
}