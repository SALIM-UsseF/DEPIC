class AdministrateurSerializer < ActiveModel::Serializer
  attributes :id_administrateur, :pseudo_administrateur, :email_administrateur, :supAdmin
end
