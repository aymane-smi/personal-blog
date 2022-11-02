import { msgError, user, userResponse } from "../utils/types";
import { User } from "../models/user";
import { NextApiRequest, NextApiResponse } from "next";

export const addUser = async (req: NextApiRequest, res: NextApiResponse<userResponse | msgError>)=>{
    try{
        const result = await User.create(req.body);
        console.log(result);
        console.log("user created!");
        res.status(200).json({
            user: result,
            message: "user created",
        });
    }catch(e){
        res.status(500).json({
            message: "something went wrong!",
        })
    }

};

export const editUser = async(req: NextApiRequest, res: NextApiResponse<userResponse | msgError>)=>{
    const {Fullname, username, password} = req.body
    try{    
        const result = await User.findByIdAndUpdate(req.body.id, {Fullname, username, password});
        console.log("user.updated!");
        res.status(200).json({
            message: "user updated!",
            user: result,
        });
    }catch(e){
        res.status(500).json({
            message: "something went wrong!",
        });
    }
};