import { connect } from "mongoose";
import { NextApiRequest, NextApiResponse } from "next";
import { editUser } from "../../../controllers/user";
import { msgError, userResponse } from "../../../utils/types";


export default async function handler(req: NextApiRequest, res: NextApiResponse<userResponse | msgError>){
    await connect(process.env.MONGODB_URL);
    console.log(process.env.MONGODB_URL);
    if(req.method === "PUT"){
        await editUser(req, res);
    }
    else
        res.status(500).json({
            message: `${req.method} not supported for this endpoint`,
        });
}; 